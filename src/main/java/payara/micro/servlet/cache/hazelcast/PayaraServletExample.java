package payara.micro.servlet.cache.hazelcast;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;
import fish.payara.cdi.jsr107.impl.NamedCache;
import fish.payara.micro.PayaraMicroRuntime;
import fish.payara.micro.data.ApplicationDescriptor;
import fish.payara.micro.data.InstanceDescriptor;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet(name = "PayaraMicroServletExample", urlPatterns = {"/PayaraMicroServletExamples"})
public class PayaraServletExample extends HttpServlet {

    @Inject
    PayaraMicroRuntime payaraRuntime;

    @Inject
    HazelcastInstance hazelcastInstance;

    @Inject
    CacheManager cacheManager;

    @NamedCache(cacheName = "Payara Examples JCache")
    @Inject
    Cache examplesCache;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // let us initialise the session
        HttpSession session = request.getSession(true);
        Long requestCount = (Long) session.getAttribute("RequestCount");
        if (requestCount == null) {
            requestCount = 0L;
        }
        session.setAttribute("RequestCount", requestCount+1);

        // Store stuff in JSR 107 JCache
        Long cacheCount = (Long) examplesCache.get("RequestCount");
        if (cacheCount == null) {
            cacheCount = 0L;
        }
        examplesCache.put("RequestCount", cacheCount+1);

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PayaraMicroExamples</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PayaraMicroExamples at " + request.getContextPath() + "</h1>");
            out.println("Cluster-wide HTTP Session request count : " + session.getAttribute("RequestCount"));
            out.println("<br/>JCache stored request count : " + examplesCache.get("RequestCount"));



            out.println("<h2>Running Payara Micro Nodes</h2><table>");
            out.println("<tr><th>Name</th><th>UUID</th><th>Host</th><th>HTTP Port</th><th>Deployed Applications</th></tr>");
            Collection<InstanceDescriptor> clusteredPayaras = payaraRuntime.getClusteredPayaras();
            for (InstanceDescriptor clusteredPayara : clusteredPayaras) {
                out.println("<tr><td>" + clusteredPayara.getInstanceName()+"</td>"
                        + "<td>" + clusteredPayara.getMemberUUID() + "</td>"
                        + "<td>" + clusteredPayara.getHostName().toString() + "</td>"
                        + "<td>" + clusteredPayara.getHttpPorts().get(0) + "</td>");
                out.println("<td><table>");
                for (ApplicationDescriptor app :  clusteredPayara.getDeployedApplications()) {
                    out.println("<tr><td>" + app.getName() +"</td></tr>");
                }
                out.println("</table></td></tr>");
            }
            out.println("</table>");

            out.println("<h2>Distributed Objects</h2><table>");
            out.println(getCacheDescriptions());


            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


    private String getCacheDescriptions() {

        StringBuilder result = new StringBuilder();

        for (DistributedObject dObject : hazelcastInstance.getDistributedObjects()) {
            result.append("HazelCast Object : ").append(dObject.getName()).append("<br/>");
        }

        for (String name : cacheManager.getCacheNames()) {
            result.append("JCache : ").append(name).append("<br/>");
        }

        return result.toString();

    }

}
