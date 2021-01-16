import event.Event;
import event.NewsEvent;
import listener.MyEventListener;
import pojo.News;

public class TestEvent {
    public static void main(String[] args) {
        News news = new News("震惊！！", "明天可以去UC震惊部上班了");
        NewsEvent event = new NewsEvent(news);

        EventSource eventSource = new EventSource();
        eventSource.addListener(new MyEventListener() {
            @Override
            public void actionPerformed(Event event) {
                if(event instanceof NewsEvent) {
                    News data = ((NewsEvent) event).getNews();
                    System.out.println(data);
                }
            }
        });

        //eventSource说有event发生了
        eventSource.publishEvent(event);
        //那些eventListener听说event发生了，纷纷对其进行自己的处理. --> 预先注册上去
    }
}
