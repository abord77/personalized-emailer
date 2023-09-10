@WebServlet("/myServlet")
public class MyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String param1 = request.getParameter("param1");
        String param2 = request.getParameter("param2");
        
        // Perform any necessary processing using the parameters
        
        // Create a JSON object to hold the response data
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("result", "success");
        
        // Set the response type to JSON
        response.setContentType("application/json");
        
        // Write the JSON response to the output stream
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

}
