package es.bisite.usal.bulltect.domain.service.impl;

import es.bisite.usal.bulltect.domain.service.IStatisticsService;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaActivityStatisticsDTO;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author sergio
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {

    @Override
    public List<SocialMediaActivityStatisticsDTO> getSocialMediaActivityStatistics(String idSon) {

        return Arrays.asList(
                new SocialMediaActivityStatisticsDTO("Facebook", "34%"),
                new SocialMediaActivityStatisticsDTO("Instagram", "23%"),
                new SocialMediaActivityStatisticsDTO("Youtube", "10%")
        );

    }
    
}
