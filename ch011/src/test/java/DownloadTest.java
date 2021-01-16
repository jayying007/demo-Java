/**
 * @author jayying
 * @date 2021/1/13
 */
public class DownloadTest {
    public static void main(String[] args) throws Exception {
        //找一个可下载的地址
        //String url = "https://down.qq.com/qqweb/PCQQ/PCQQ_EXE/PCQQ2020.exe";
        String url = "http://d1.music.126.net/dmusic/netease-cloud-music_1.2.1_amd64_ubuntu_20190428.deb";
        //生成下载对象
        DownloadUtil downloadUtil = new DownloadUtil(url, "ch011/netease-cloud-music.deb", 8);
        //开始下载
        downloadUtil.download();
    }
}
