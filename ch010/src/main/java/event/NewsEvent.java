package event;

import pojo.News;

public class NewsEvent extends Event {
    private final News news;
    /**
     * Constructs a prototypical Event.
     *
     * @param news The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public NewsEvent(News news) {
        super(news);
        this.news = news;
    }

    public News getNews() {
        return news;
    }
}
