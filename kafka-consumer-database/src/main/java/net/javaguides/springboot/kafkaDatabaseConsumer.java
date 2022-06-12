package net.javaguides.springboot;


import lombok.AllArgsConstructor;
import net.javaguides.springboot.entity.WikimediaData;
import net.javaguides.springboot.repo.WikimediaRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class kafkaDatabaseConsumer {
    public static final Logger LOGGER= LoggerFactory.getLogger(kafkaDatabaseConsumer.class);

    private WikimediaRepo dataRepository;

    @KafkaListener(
            topics = "wikimedia_recentchange",
            groupId = "myGroup"
    )
    public void consume(String eventMessage){
        LOGGER.info(String.format("Message recieved-> %s",eventMessage));
        WikimediaData wikimediaData=new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
        dataRepository.save(wikimediaData);
    }
}
