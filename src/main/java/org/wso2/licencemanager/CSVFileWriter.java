package org.wso2.licencemanager;

import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * Author:Jayathma Chathuranagani
 *
 */
public class CSVFileWriter
{
    public static void main( String[] args )  throws Exception
    {

        //j is the number of pages that give non empty results
        for(int j=1;j<6;j++){
            String filepath = "/home/jayathma/WSO2_Intern/Aether/DependencyManager_Project/CSVfiles_RepoUrls/WSO2repolist"+j+".csv";
            PrintWriter pw = new PrintWriter(new File(filepath));
            StringBuilder sb = new StringBuilder();

            String url = "https://api.github.com/orgs/wso2/repos?page="+j+"&per_page=50";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int responseCode = con.getResponseCode();

//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String s="{\"array\":"+response.toString()+"}";
            //print result

            JSONParser parse = new JSONParser();
            JSONObject jobj = (JSONObject)parse.parse(s);
            JSONArray arr = (JSONArray)jobj.get("array") ;

            for(int i=0;i<arr.size();i++){
                JSONObject obj_1 = (JSONObject)arr.get(i);
                System.out.println(obj_1.get("name").toString());
                System.out.println(obj_1.get("html_url").toString());
                sb.append(obj_1.get("name").toString());
                sb.append(',');
                sb.append(obj_1.get("html_url").toString());
                sb.append(',');
                sb.append('\n');
                System.out.println("done!");
            }
            pw.write(sb.toString());
            pw.close();
        }

        System.out.println("done!");
    }
}
