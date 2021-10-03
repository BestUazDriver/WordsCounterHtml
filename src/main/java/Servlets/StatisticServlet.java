package Servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getParameter("folder");
        Map<String, Integer> counterMap = new StatisticServlet().counter(path);
        Set<String> keySet=counterMap.keySet();
        List<String> words= new ArrayList<>(keySet);
        String htmlResponse="<h1>Count statistics according to file.txt on directory from your computer: "+path+ "<b1><h1>";
        resp.setContentType("text/html");
        resp.getWriter().println(htmlResponse);

        for(int i=0;i<counterMap.size();i++){
            resp.getWriter().write("<h2>"+words.get(i)+"   "+ counterMap.get(words.get(i))+"<b1><h2>");
        }


    }
    private Map<String, Integer> counter(String path) throws FileNotFoundException {
        Map<String, Integer> counterMap = new HashMap<>();
        List<String> words = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("[()',.-?!\"]", "");
                System.out.println(line);
                String[] atributes = line.split(" ");
                words.addAll(Arrays.asList(atributes));
            }
            for (int i = 0; i < words.size(); i++) {
                counterMap.put(words.get(i), Collections.frequency(words, words.get(i)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counterMap;
    }
}
