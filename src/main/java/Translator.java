import javax.net.ssl.HttpsURLConnection;
import java.beans.Encoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Translator {
    public String translate(String word) throws UnsupportedEncodingException {
        String r = new String(word.getBytes(), "cp1251");
        //Encoder encoder = new Encoder();
        PrintStream ps = null;
//        try {
//            ps = new PrintStream(System.out, false, "cp1251");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
////        var a = word.getBytes();
////        for (int i=0;i<a.length;++i){
////            ps.print((int)a[i]+"\n");
////        }
//        //ps.println(r);
        String translateWord = null;
        r = URLEncoder.encode(r, "utf-8");
        //https://www.translate.ru/%D0%BF%D0%B5%D1%80%D0%B5%D0%B2%D0%BE%D0%B4/%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8%D0%B9-%D0%B0%D0%BD%D0%B3%D0%BB%D0%B8%D0%B9%D1%81%D0%BA%D0%B8%D0%B9/%D0%BF%D1%83%D1%81%D1%82%D0%BE%D1%82%D0%B0
        //URL url  = new URL("https://tts.voicetech.yandex.net/generate?text="+r+"&format=mp3&lang=ru-RU&speaker=zahar&emotion=good&key=");
        String url = "https://www.translate.ru/%D0%BF%D0%B5%D1%80%D0%B5%D0%B2%D0%BE%D0%B4/%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8%D0%B9-%D0%B0%D0%BD%D0%B3%D0%BB%D0%B8%D0%B9%D1%81%D0%BA%D0%B8%D0%B9/"+r.toString();
        //System.out.println(url);
        HttpsURLConnection connection = null;

        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(500);
            connection.setReadTimeout(500);

            connection.connect();

            StringBuilder sb = new StringBuilder();

            if (HttpsURLConnection.HTTP_OK == connection.getResponseCode()){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                int i=0;
                while ((line = in.readLine()) != null){
                    ++i;
                    sb.append(line);
                    sb.append("\n");
                    if (line.contains("textResult")){
                        translateWord = line;
                        //System.out.println(line);
                    }
                }
                //System.out.println(sb);
            }else{
                System.out.println("errrror");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        translateWord = translateWord.substring(translateWord.indexOf('>')+1,translateWord.indexOf("</"));
        //System.out.println(translateWord);
        return  translateWord;
    }
}
