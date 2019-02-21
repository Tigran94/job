package clientSide.services;

import clientSide.entities.UserEntity;
import clientSide.entities.UserHistory;
import clientSide.repositories.UserHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class UserHistoryService {

    private UserHistoryRepository userHistoryRepository;

    public UserHistoryService(UserHistoryRepository userHistoryRepository){
        this.userHistoryRepository = userHistoryRepository;
    }


    public UserHistory save(UserHistory userHistory){
       return userHistoryRepository.save(userHistory);
    }

    public UserHistory converUserToHistory(UserEntity userEntity){
        UserHistory userHistory = new UserHistory();
        userHistory.setUserName(userEntity.getUsername());
        userHistory.setUserEntity(userEntity);
        return userHistory;
    }
}
