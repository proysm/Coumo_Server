package coumo.server.service.notice;

import coumo.server.domain.Notice;
import coumo.server.domain.Store;
import coumo.server.repository.NoticeRepository;
import coumo.server.repository.OwnerRepository;
import coumo.server.repository.StoreRepository;
import coumo.server.web.dto.NoticeRequestDTO;
import coumo.server.web.dto.NoticeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public Notice postNotice(Store store, NoticeRequestDTO.updateNoticeDTO dto) {

        Notice newNotice = dto.toEntity(store);
        return noticeRepository.save(newNotice);
    }

    @Override
    public NoticeResponseDTO.MyNoticeListDTO readNotice(Store store) {

        Optional<List<NoticeResponseDTO.NoticeThumbInfo>> noticeThumbInfo = noticeRepository.findAllByStore(store);

        NoticeResponseDTO.MyNoticeListDTO myNoticeListDTO = NoticeResponseDTO.MyNoticeListDTO.builder()
                .total(noticeThumbInfo.get().size())
                .notice(noticeThumbInfo.get())
                .build();

        return myNoticeListDTO;
    }

    @Override
    public NoticeResponseDTO.MyNoticeDetail readNoticeDetail(Long noticeId) {

        Notice notice = noticeRepository.findById(noticeId).get();
        NoticeResponseDTO.MyNoticeDetail myNoticeDetail = NoticeResponseDTO.MyNoticeDetail.builder()
                .noticeType(notice.getNoticeType())
                .title(notice.getTitle())
                .noticeContent(notice.getNoticeContent())
                .image(notice.getImage())
                .build();

        return myNoticeDetail;
    }

    @Override
    public void updateNotice(Long noticeId, NoticeRequestDTO.updateNoticeDTO dto) {

        Notice notice = noticeRepository.findById(noticeId).get();
        Notice newNotice = Notice.builder()
                .id(noticeId)
                .noticeType(notice.getNoticeType())
                .title(notice.getTitle())
                .noticeContent(notice.getNoticeContent())
                .image(notice.getImage())
                .build();

        noticeRepository.save(newNotice);
    }

    @Override
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    @Override
    public Notice findNotice(Long noticeId) {
        return noticeRepository.findById(noticeId).get();
    }
}
