package Servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//http://localhost:8081/wordscounter?folder=C:/Users/Пользователь/Desktop/2course/JavaLab/WordsCounterWeb/src/main/java/Info/file.txt

@WebServlet(urlPatterns = "/wordscounter")
public class StatisticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getParameter("folder");
        Map<String, Integer> counterMap = new StatisticServlet().counter(path);
        Set<String> keySet = counterMap.keySet();
        List<String> words = new ArrayList<>(keySet);

        String htmlResponse = "<h1>Count statistics according to file.txt on directory from your computer: " + path + "<b1><h1>";
        resp.setContentType("text/html");
        try {
            resp.getWriter().println(htmlResponse);
            for (int i = 0; i < counterMap.size(); i++) {
                resp.getWriter().write("<h1>" + words.get(i) + "   " + counterMap.get(words.get(i)) + "<br><h1>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Map<String, Integer> counter(String path) {
        Map<String, Integer> counterMap = new HashMap<>();
        List<String> words = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("[()',.-?!\"]", "");
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
