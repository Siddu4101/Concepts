package generalJavaQuestions.commonQuestions.desingPattern;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Simple design*/
class YoutubeChannel{
    private final String channelName;
    private final List<ISubscriber> subscribers = new ArrayList<>();

    YoutubeChannel(String channelName) {
        this.channelName = channelName;
    }

    public void subscribe(ISubscriber subscriber){
        subscribers.add(subscriber);
    }

    public void unsubscribe(ISubscriber subscriber){
        subscribers.remove(subscriber);
    }

    public void publishVideo(String videoTitle){
        subscribers.forEach(s -> s.update(videoTitle, channelName));
    }
}

interface ISubscriber{
    void update(String vidTitle, String channelName);
}

@Slf4j
class Subscriber implements ISubscriber{
    private final String name;
    private final int id;

    Subscriber(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public void update(String vidTitle, String channelName) {
        log.info("Hey {}:{} you have new video notification {} from channel {}", id,name,vidTitle, channelName);
    }
}

class TestObserverPattern{
    public static void main(String[] args) {
        YoutubeChannel sidChannel = new YoutubeChannel("SidChannel");
        ISubscriber sid = new Subscriber("Sid", 1);
        ISubscriber raj = new Subscriber("Raj", 2);
        ISubscriber viju = new Subscriber("Viju", 3);

        sidChannel.subscribe(sid);
        sidChannel.subscribe(raj);
        sidChannel.subscribe(viju);

        sidChannel.unsubscribe(viju);

        sidChannel.publishVideo("Observer pattern");


    }
}


/*You can have multiple listener family
* 1. video subscribers
* 2. shorts subscribers etc..
* */

@Slf4j
class ShortsSubscriber implements ISubscriber {
    private final String name;
    private final int id;

    ShortsSubscriber(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public void update(String shortsTitle, String channelName) {
        log.info("Hey {}:{} you have new shorts notification {} from channel {}", id,name,shortsTitle, channelName);
    }
}

class YoutubeChannelWithMultiKindSubscriber{
    private final String channelName;
    private final Map<String, List<ISubscriber>> subscribersByKind = new HashMap<>();


    public YoutubeChannelWithMultiKindSubscriber(String channelName) {
        this.channelName = channelName;
        subscribersByKind.put("video", new ArrayList<>());
        subscribersByKind.put("shorts", new ArrayList<>());
    }

    public void subscribe(String kind, ISubscriber subscriber){
        subscribersByKind.computeIfAbsent(kind, ignored -> new ArrayList<>()).add(subscriber);
    }

    public void unsubscribe(String kind, ISubscriber subscriber){
        subscribersByKind.getOrDefault(kind, new ArrayList<>()).remove(subscriber);
    }

    public void publish(String kind, String title){
        subscribersByKind.getOrDefault(kind, new ArrayList<>())
                .forEach(s -> s.update(title, channelName));
    }

}

class TestMultiKindSubscriberObserver{
    public static void main(String[] args) {
        YoutubeChannelWithMultiKindSubscriber channel = new YoutubeChannelWithMultiKindSubscriber("sidMultiChannel");

        ISubscriber sid = new ShortsSubscriber("sid", 1);
        ISubscriber raj = new Subscriber("raj", 2);
        ISubscriber manu = new Subscriber("manu", 3);
        ISubscriber santu = new Subscriber("santu", 4);
        ISubscriber viju = new ShortsSubscriber("viju", 5);

        channel.subscribe("video", raj);
        channel.subscribe("video", manu);
        channel.subscribe("video", santu);
        channel.subscribe("shorts", sid);
        channel.subscribe("shorts", viju);

        channel.unsubscribe("video", santu);

        channel.publish("video", "Toxic new movie");
        channel.publish("shorts", "Ramayana teaser");
    }
}

