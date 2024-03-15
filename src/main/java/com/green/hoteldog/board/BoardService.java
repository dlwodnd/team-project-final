package com.green.hoteldog.board;

import com.green.hoteldog.board.models.*;
import com.green.hoteldog.common.Const;
import com.green.hoteldog.common.entity.BoardCommentEntity;
import com.green.hoteldog.common.entity.BoardEntity;
import com.green.hoteldog.common.entity.BoardPicsEntity;
import com.green.hoteldog.common.entity.UserEntity;
import com.green.hoteldog.common.repository.*;
import com.green.hoteldog.common.utils.CommonUtils;
import com.green.hoteldog.common.utils.MyFileUtils;
import com.green.hoteldog.common.ResVo;
import com.green.hoteldog.common.utils.RandomCodeUtils;
import com.green.hoteldog.exceptions.AuthorizedErrorCode;
import com.green.hoteldog.exceptions.BoardErrorCode;
import com.green.hoteldog.exceptions.CommonErrorCode;
import com.green.hoteldog.exceptions.CustomException;
import com.green.hoteldog.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardMapper boardMapper;
    private final MyFileUtils fileUtils;
    private final AuthenticationFacade facade;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardPicsRepository boardPicsRepository;


    //---------------------------------------------------게시글 등록 Mybatis------------------------------------------------------
    /*public ResVo postBoard(PostBoardDto dto) {
        dto.setUserPk((int)facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        log.info("postDto : {}", dto);
        dto.setBoardNum("B" + RandomCodeUtils.getRandomCode(5));
        try {
            boardMapper.postBoard(dto);
        } catch (Exception e) {
            return new ResVo(0);
        }
        if (dto.getPics() != null) {
            List<String> pics = new ArrayList<>();
            String target = "/board/" + dto.getBoardPk();
            for (MultipartFile file : dto.getPics()) {
                String saveFileNm = fileUtils.transferTo(file, target);
                pics.add(saveFileNm);
            }
            PostBoardPicDto picsDto = new PostBoardPicDto();
            picsDto.setBoardPk(dto.getBoardPk());
            picsDto.setPics(pics);
            try {
                boardMapper.postBoardPics(picsDto);
            } catch (Exception e) {
                return new ResVo(0);
            }
        }
        return new ResVo(1);
    }*/
    @Transactional
    public ResVo postBoard(PostBoardDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        BoardEntity boardEntity = BoardEntity.builder()
                .boardCategoryEntity(boardCategoryRepository.findById(dto.getBoardCategoryPk())
                        .orElseThrow(() -> new CustomException(BoardErrorCode.BAD_REQUEST_BOARD_CATEGORY_PK)))
                .userEntity(userEntity)
                .boardNum("B" + RandomCodeUtils.getRandomCode(5))
                .title(dto.getTitle())
                .contents(dto.getContents())
                .boardViewCount(0L)
                .build();
        boardRepository.save(boardEntity);
        if (dto.getPics() != null && !dto.getPics().isEmpty()) {
            Set<BoardPicsEntity> boardPicsEntityList = new HashSet<>();
            String target = "/board/" + boardEntity.getBoardPk();
            for (MultipartFile file : dto.getPics()) {
                String saveFileNm = fileUtils.transferTo(file, target);
                BoardPicsEntity boardPicsEntity = BoardPicsEntity.builder()
                        .boardEntity(boardEntity)
                        .pic(saveFileNm)
                        .build();
                boardPicsEntityList.add(boardPicsEntity);
            }
            boardEntity.setBoardPicsEntityList(boardPicsEntityList);
        }
        return new ResVo(Const.SUCCESS);
    }


    //---------------------------------------------------게시글 수정------------------------------------------------------
    /*@Transactional //Mybatis version
    public ResVo putBoard(PutBoardDto dto) {
        dto.setUserPk((int)facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        int result = boardMapper.putBoard(dto);
        if (result == 0) {
            return new ResVo(0);
        }
        if (dto.getPics() != null) {
            String target = "/board/" + dto.getBoardPk();
            try {
                fileUtils.delFolderTrigger(target);
                boardMapper.delBoardPics(dto.getBoardPk());
            } catch (Exception e) {
                throw new CustomException(CommonErrorCode.ACCEPTED);
            }
            List<String> pics = new ArrayList<>();
            for (MultipartFile file : dto.getPics()) {
                String saveFileNm = fileUtils.transferTo(file, target);
                pics.add(saveFileNm);
            }

            PostBoardPicDto picsDto = new PostBoardPicDto();
            picsDto.setBoardPk(dto.getBoardPk());
            picsDto.setPics(pics);
            try {
                boardMapper.postBoardPics(picsDto);
            } catch (Exception e) {
                throw new CustomException(CommonErrorCode.ACCEPTED);
            }
        }
        return new ResVo(1);
    }*/
    @Transactional
    public ResVo putBoard(PutBoardDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        BoardEntity boardEntity = boardRepository.findById(dto.getBoardPk()).orElseThrow(() -> new CustomException(BoardErrorCode.BAD_REQUEST_BOARD_PK));
        if (!boardEntity.getUserEntity().equals(userEntity)) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        boardEntity.setBoardCategoryEntity(boardCategoryRepository.findById(dto.getBoardCategoryPk())
                .orElseThrow(() -> new CustomException(BoardErrorCode.BAD_REQUEST_BOARD_CATEGORY_PK)));
        boardEntity.setTitle(dto.getTitle());
        boardEntity.setContents(dto.getContents());
        String target = "/board/" + boardEntity.getBoardPk();
        if (dto.getPicsPk() != null && !dto.getPicsPk().isEmpty()) {
            List<BoardPicsEntity> boardPicsEntityList = boardPicsRepository.findAllById(dto.getPicsPk());
            List<String> delPics = boardPicsEntityList.stream().map(BoardPicsEntity::getPic).toList();
            for (String delPic : delPics) {
                String delFIle = "/" + delPic;
                fileUtils.delFile(target, delFIle);
            }
            boardPicsRepository.deleteAll(boardPicsEntityList);
        }

        if (dto.getPics() != null && !dto.getPics().isEmpty()) {
            Set<BoardPicsEntity> boardPicsEntityList = new HashSet<>();
            for (MultipartFile file : dto.getPics()) {
                String saveFileNm = fileUtils.transferTo(file, target);
                BoardPicsEntity boardPicsEntity = BoardPicsEntity.builder()
                        .boardEntity(boardEntity)
                        .pic(saveFileNm)
                        .build();
                boardPicsEntityList.add(boardPicsEntity);
            }
            boardEntity.setBoardPicsEntityList(boardPicsEntityList);
            boardEntity.getBoardPicsEntityList().addAll(boardPicsEntityList);
        }

        return new ResVo(Const.SUCCESS);
    }

    //---------------------------------------------------게시글 좋아요 임시 중단-------------------------------------------
    /*
    public ResVo putBoardFav(BoardFavDto dto){
        dto.setUserPk(facade.getLoginUserPk());
        if(dto.getUserPk() == 0){
            //예외처리 로그인 하지 않은 유저는 좋아요 할 수 없음
        }
        if(boardRepository.delFav(dto) == 0){
            boardRepository.postFav(dto);
            return new ResVo(1);
        }
        return new ResVo(2);
    }
    */
    //---------------------------------------------------게시글 삭제------------------------------------------------------
    /*@Transactional(rollbackFor = Exception.class) //Mybatis version
    public ResVo deleteBoard(DeleteBoardDto dto) {
        dto.setUserPk((int)facade.getLoginUserPk());
        log.info("DeleteBoardDto : {}", dto);
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        boardMapper.delBoardListPics(dto.getBoardPkList());
        boardMapper.delBoardComment(dto.getBoardPkList());
        int result = boardMapper.delBoard(dto);
        log.info("result : {} , dto.getBoardPkList().size() : {}", result, dto.getBoardPkList().size());
        if (result != dto.getBoardPkList().size()) {
            throw new CustomException(BoardErrorCode.BAD_REQUEST_BOARD_PK);
        }

        for (Integer boardPk : dto.getBoardPkList()) {
            String target = "/board/" + boardPk;
            fileUtils.delAllFolderTrigger(target);
        }
        return new ResVo(Const.SUCCESS);
    }*/
    @Transactional
    public ResVo deleteBoard(DeleteBoardDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        List<BoardEntity> boardEntityList = boardRepository.findAllById(dto.getBoardPkList());
        for (BoardEntity boardEntity : boardEntityList) {
            if (!boardEntity.getUserEntity().equals(userEntity)) {
                throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
            }
        }
        for (BoardEntity boardEntity : boardEntityList) {
            String target = "/board/" + boardEntity.getBoardPk();
            fileUtils.delAllFolderTrigger(target);
        }
        boardRepository.deleteAll(boardEntityList);
        return new ResVo(Const.SUCCESS);
    }

    //---------------------------------------------------댓글 등록--------------------------------------------------------
    /*public ResVo postComment(PostCommentDto dto) { //Mybatis version
        dto.setUserPk((int)facade.getLoginUserPk());
        dto.setCommentNum("Z" + RandomCodeUtils.getRandomCode(5));
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        int result = boardMapper.postComment(dto);
        return new ResVo(result);
    }*/
    @Transactional
    public ResVo postComment(PostCommentDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        BoardEntity boardEntity = boardRepository.findById(dto.getBoardPk()).orElseThrow(() -> new CustomException(BoardErrorCode.BAD_REQUEST_BOARD_PK));
        BoardCommentEntity boardCommentEntity = BoardCommentEntity.builder()
                .boardEntity(boardEntity)
                .userEntity(userEntity)
                .comment(dto.getComment())
                .commentNum("Z" + RandomCodeUtils.getRandomCode(5))
                .build();
        boardEntity.getBoardCommentEntityList().add(boardCommentEntity);
        boardRepository.save(boardEntity);
        return new ResVo(Const.SUCCESS);
    }

    //---------------------------------------------------댓글 삭제--------------------------------------------------------
    /*@Transactional(rollbackFor = Exception.class) //Mybatis version
    public ResVo deleteComment(DeleteCommentDto dto) {
        dto.setUserPk((int) facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        int result = boardMapper.delComment(dto);
        if (result != dto.getCommentPkList().size()) {
            throw new CustomException(BoardErrorCode.BAD_REQUEST_BOARD_PK);
        }
        return new ResVo(Const.SUCCESS);
    }*/
    @Transactional
    public ResVo deleteComment(DeleteCommentDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        List<BoardCommentEntity> boardCommentEntityList = boardCommentRepository.findAllById(dto.getCommentPkList());
        for (BoardCommentEntity boardCommentEntity : boardCommentEntityList) {
            if (!boardCommentEntity.getUserEntity().equals(userEntity)) {
                throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
            }
        }
        boardCommentRepository.deleteAll(boardCommentEntityList);
        return new ResVo(Const.SUCCESS);
    }

    //---------------------------------------------------댓글 수정--------------------------------------------------------
    /*public ResVo updateComment(PutCommentDto dto) { //Mybatis version
        dto.setUserPk((int) facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        int result = boardMapper.updComment(dto);
        return new ResVo(result);
    }*/
    @Transactional
    public ResVo updateComment(PutCommentDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        BoardCommentEntity boardCommentEntity = boardCommentRepository.findById(dto.getCommentPk())
                .orElseThrow(() -> new CustomException(BoardErrorCode.BAD_REQUEST_COMMENT_PK));
        if (!boardCommentEntity.getUserEntity().equals(userEntity)) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        boardCommentEntity.setComment(dto.getComment());
        return new ResVo(Const.SUCCESS);
    }

    //---------------------------------------------------게시글 리스트----------------------------------------------------
    /*public GetSimpleBoardVo getBoardList(GetBoardListDto dto) { //Mybatis version
        GetSimpleBoardVo vo = new GetSimpleBoardVo();
        vo.setSimpleBoardVoList(boardMapper.getBoardList(dto));
        for (SimpleBoardVo boardVo : vo.getSimpleBoardVoList()) {
            boardVo.setCreatedAt(boardVo.getCreatedAt().substring(0, boardVo.getCreatedAt().lastIndexOf(".")));
        }
        int boardCount = boardMapper.selBoardCount(dto);
        int maxPage = 1;
        if (boardCount != 0) {
            maxPage = this.maxPage(boardCount, dto.getRowCount());
        }
        vo.setMaxPage(maxPage);
        return vo;
    }*/
    @Transactional
    public GetSimpleBoardVo getBoardList(GetBoardListDto dto) {
        Page<BoardEntity> boardEntities = boardRepository.boardListFilter(dto);
        List<SimpleBoardVo> simpleBoardVoList = boardEntities.getContent()
                .stream()
                .map(item -> SimpleBoardVo.builder()
                        .title(item.getTitle())
                        .boardPk(item.getBoardPk())
                        .boardCategoryPk(item.getBoardCategoryEntity().getBoardCategoryPk())
                        .userPk(item.getUserEntity().getUserPk())
                        .boardViewCount(item.getBoardViewCount())
                        .categoryNm(item.getBoardCategoryEntity().getCategoryNm())
                        .nickname(item.getUserEntity().getNickname())
                        .createdAt(item.getCreatedAt().toString())
                        .build())
                .collect(Collectors.toList());
        GetSimpleBoardVo vo = new GetSimpleBoardVo();
        vo.setSimpleBoardVoList(simpleBoardVoList);
        vo.setMaxPage(boardEntities.getTotalPages());
        return vo;

    }

    //---------------------------------------------------게시글 정보------------------------------------------------------
        /*public GetBoardInfoVo getBoardInfo (GetBoardInfoDto dto){ //Mybatis version
            boardMapper.boardViewCount(dto.getBoardPk());
            GetBoardInfoVo vo = boardMapper.getBoardInfo(dto.getBoardPk());
            vo.setPics(boardMapper.selBoardPics(dto.getBoardPk()));
            return vo;
        }*/
    @Transactional
    public GetBoardInfoVo getBoardInfo(GetBoardInfoDto dto) {
        BoardEntity boardEntity = boardRepository.findById(dto.getBoardPk())
                .orElseThrow(() -> new CustomException(BoardErrorCode.BAD_REQUEST_BOARD_PK));

        boardEntity.setBoardViewCount(boardEntity.getBoardViewCount() + 1);
        boardRepository.save(boardEntity);
        GetBoardInfoVo vo = GetBoardInfoVo.builder()
                .boardPk(boardEntity.getBoardPk())
                .boardCategoryPk(boardEntity.getBoardCategoryEntity().getBoardCategoryPk())
                .title(boardEntity.getTitle())
                .userPk(boardEntity.getUserEntity().getUserPk())
                .contents(boardEntity.getContents())
                .nickname(boardEntity.getUserEntity().getNickname())
                .createdAt(boardEntity.getCreatedAt().toString())
                .boardViewCount(boardEntity.getBoardViewCount())
                .pics(boardEntity.getBoardPicsEntityList().stream().map(BoardPicsEntity::getPic).toList())
                .build();

        return vo;

    }


    //--------------------------------------------게시글에 등록된 댓글 리스트------------------------------------------------
        /*public BoardCommentVo getBoardComment (GetBoardCommentDto dto){ //Mybatis version
            List<CommentInfoVo> boardComment = boardMapper.selBoardComment(dto);
            for (CommentInfoVo infoVo : boardComment) {
                infoVo.setCreatedAt(infoVo.getCreatedAt().substring(0, infoVo.getCreatedAt().lastIndexOf(".")));
            }
            BoardCommentVo vo = new BoardCommentVo();
            vo.setCommentInfoVoList(boardComment);
            int commentCount = boardMapper.selBoardCommentCount(dto.getBoardPk());
            vo.setCommentCount(commentCount);
            int commentMaxPage = this.maxPage(commentCount, dto.getRowCount());
            if (commentMaxPage == 0) {
                commentMaxPage = 1;
            }
            vo.setCommentMaxPage(commentMaxPage);
            return vo;
        }*/
    @Transactional
    public BoardCommentVo getBoardComment(GetBoardCommentDto dto) {
        Page<BoardCommentEntity> boardCommentEntities = boardCommentRepository.findAllByBoardEntity_BoardPkOrderByCreatedAtAsc(dto.getBoardPk(), dto.getPageable());
        List<CommentInfoVo> commentInfoVoList = boardCommentEntities.getContent()
                .stream()
                .map(item -> CommentInfoVo.builder()
                        .commentPk(item.getCommentPk())
                        .userPk(item.getUserEntity().getUserPk())
                        .userNickname(item.getUserEntity().getNickname())
                        .comment(item.getComment())
                        .createdAt(item.getCreatedAt().toString())
                        .build())
                .collect(Collectors.toList());
        BoardCommentVo vo = new BoardCommentVo();
        vo.setCommentInfoVoList(commentInfoVoList);
        vo.setCommentCount(boardCommentEntities.getTotalElements());
        vo.setCommentMaxPage(boardCommentEntities.getTotalPages());
        return vo;
    }

    //--------------------------------------------로그인 유저가 작성한 게시글-----------------------------------------------

    /*public GetSimpleBoardVo userPostingBoardList(GetUserBoardListDto dto) {
        dto.setUserPk((int) facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        GetSimpleBoardVo vo = new GetSimpleBoardVo();
        vo.setSimpleBoardVoList(boardMapper.myPostingBoardList(dto));
        for (SimpleBoardVo boardVo : vo.getSimpleBoardVoList()) {
            boardVo.setCreatedAt(boardVo.getCreatedAt().substring(0, boardVo.getCreatedAt().indexOf(".")));
        }
        int userBoardCount = boardMapper.selUserBoardCount(dto.getUserPk());
        int userBoardMaxPage = 1;
        if (userBoardCount != 0) {
            userBoardMaxPage = this.maxPage(userBoardCount, dto.getRowCount());
        }
        vo.setMaxPage(userBoardMaxPage);
        return vo;
    }*/
    @Transactional
    public GetSimpleBoardVo userPostingBoardList(GetUserBoardListDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        Page<BoardEntity> boardEntities = boardRepository.findAllByUserEntityOrderByBoardPk(userEntity, dto.getPageable());
        List<SimpleBoardVo> simpleBoardVoList = boardEntities.getContent()
                .stream()
                .map(item -> SimpleBoardVo.builder()
                        .title(item.getTitle())
                        .boardPk(item.getBoardPk())
                        .boardCategoryPk(item.getBoardCategoryEntity().getBoardCategoryPk())
                        .userPk(item.getUserEntity().getUserPk())
                        .boardViewCount(item.getBoardViewCount())
                        .categoryNm(item.getBoardCategoryEntity().getCategoryNm())
                        .nickname(item.getUserEntity().getNickname())
                        .createdAt(item.getCreatedAt().toString())
                        .build())
                .collect(Collectors.toList());
        GetSimpleBoardVo vo = new GetSimpleBoardVo();
        vo.setSimpleBoardVoList(simpleBoardVoList);
        vo.setMaxPage(boardEntities.getTotalPages());
        return vo;
    }

    //--------------------------------------------로그인 유저가 작성한 댓글-------------------------------------------------
    /*public GetUserCommentVo userPostingCommentList(GetUserCommentListDto dto) { //Mybatis version
        dto.setUserPk((int) facade.getLoginUserPk());
        if (dto.getUserPk() == 0) {
            throw new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED);
        }
        GetUserCommentVo vo = new GetUserCommentVo();
        vo.setUserCommentVoList(boardMapper.myPostingCommentList(dto));
        for (UserCommentVo userCommentVo : vo.getUserCommentVoList()) {
            userCommentVo.setCreatedAt(userCommentVo.getCreatedAt().substring(0, userCommentVo.getCreatedAt().lastIndexOf(".")));
        }
        int userCommentCount = boardMapper.selUserCommentCount(dto.getUserPk());
        int userCommentMaxPage = 1;
        if (userCommentCount != 0) {
            userCommentMaxPage = this.maxPage(boardMapper.selUserCommentCount(dto.getUserPk()), dto.getRowCount());
        }
        vo.setMaxPage(userCommentMaxPage);
        return vo;
    }*/
    @Transactional
    public GetUserCommentVo userPostingCommentList(GetUserCommentListDto dto) {
        UserEntity userEntity = userRepository.findById(facade.getLoginUserPk())
                .orElseThrow(() -> new CustomException(AuthorizedErrorCode.NOT_AUTHORIZED));
        Page<BoardCommentEntity> boardCommentEntities = boardCommentRepository.findAllByUserEntityOrderByCreatedAtDesc(userEntity, dto.getPageable());
        List<UserCommentVo> userCommentVoList = boardCommentEntities.getContent()
                .stream()
                .map(item -> UserCommentVo.builder()
                        .boardPk(item.getBoardEntity().getBoardPk())
                        .title(item.getBoardEntity().getTitle())
                        .boardCategoryPk(item.getBoardEntity().getBoardCategoryEntity().getBoardCategoryPk())
                        .categoryNm(item.getBoardEntity().getBoardCategoryEntity().getCategoryNm())
                        .commentPk(item.getCommentPk())
                        .comment(item.getComment())
                        .userPk(item.getUserEntity().getUserPk())
                        .nickname(item.getUserEntity().getNickname())
                        .createdAt(item.getCreatedAt().toString())
                        .boardViewCount(item.getBoardEntity().getBoardViewCount())
                        .build())
                .collect(Collectors.toList());
        GetUserCommentVo vo = new GetUserCommentVo();
        vo.setUserCommentVoList(userCommentVoList);
        vo.setMaxPage(boardCommentEntities.getTotalPages());
        return vo;
    }

    //------------------------------------------------총 페이지 수 계산---------------------------------------------------
    public int maxPage(int columnCount, int rowCount) {
        return (int) Math.ceil((double) columnCount / rowCount);
    }
}
