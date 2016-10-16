package cookie.cn.readlater;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DemoTest {

    public static void main(String[] args) throws IOException {
        String url = "http://ocookie.cn/ji-yu-mqttzi-ding-yi-yi-dong-duan-tui-song-fang-an";

		/* 注意：本处只为展示抽取效果，不处理网页编码问题，getHTML只能接收GBK编码的网页，否则会出现乱码 */
        String content = getHTML(url);
        /*
         * 当待抽取的网页正文中遇到成块的新闻标题未剔除时，只要增大此阈值即可。
		 * 相反，当需要抽取的正文内容长度较短，比如只有一句话的新闻，则减小此阈值即可。
		 * 阈值增大，准确率提升，召回率下降；值变小，噪声会大，但可以保证抽到只有一句话的正文 
		 */
        //WebValidTextExtract.setThreshold(76); // 默认值86

//        System.out.println("大家看");
        System.out.println(WebValidTextExtract.parse(content, url, false));
    }


    public static String getHTML(String strURL) throws IOException {
        URL url = new URL(strURL);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String s = "";
        StringBuilder sb = new StringBuilder("");
        while ((s = br.readLine()) != null) {
            sb.append(s + "\n");
        }
        return sb.toString();
    }
}
