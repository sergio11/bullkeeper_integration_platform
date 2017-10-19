package es.bisite.usal.bulltect.domain.service;

import es.bisite.usal.bulltect.web.dto.response.SocialMediaActivityStatisticsDTO;
import java.util.List;

/**
 *
 * @author sergio
 */
public interface IStatisticsService {
    List<SocialMediaActivityStatisticsDTO> getSocialMediaActivityStatistics(String idSon);
}
