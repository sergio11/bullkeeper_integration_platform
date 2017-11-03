package es.bisite.usal.bulltect.persistence.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.persistence.entity.AnalysisStatusEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.web.dto.response.CommentsBySonDTO;



/**
 *
 * @author sergio
 */
public interface CommentRepositoryCustom {
    List<CommentsBySonDTO> getCommentsBySon();
    void updateAnalysisStatusFor(final AnalysisTypeEnum type, final AnalysisStatusEnum from, final AnalysisStatusEnum to, final Collection<ObjectId> ids);
    void updateAnalysisStatusFor(AnalysisTypeEnum type, AnalysisStatusEnum from, AnalysisStatusEnum to);
    void updateAnalysisStatusFor(final AnalysisTypeEnum type, final AnalysisStatusEnum status, final Collection<ObjectId> ids);
    void startAnalysisFor(final AnalysisTypeEnum type, final Collection<ObjectId> ids);
    void startSentimentAnalysisFor(Collection<ObjectId> ids);
    void startViolenceAnalysisFor(Collection<ObjectId> ids);
    void startDrugsAnalysisFor(Collection<ObjectId> ids);
    void startAdultAnalysisFor(Collection<ObjectId> ids);
    void startBullyingAnalysisFor(Collection<ObjectId> ids);
    void cancelAnalyzesThatAreTakingMoreThanNHours(final AnalysisTypeEnum type, Integer hours);
    Date getExtractedAtOfTheLastCommentBySocialMediaAndSonId(final SocialMediaTypeEnum socialMedia, final ObjectId sonId);
}
