package clientSide.services;

import clientSide.entities.CompanyEntity;
import clientSide.entities.CompanyHistory;
import clientSide.repositories.CompanyHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyHistoryService {

    public CompanyHistoryRepository historyRepository;

    public CompanyHistoryService(CompanyHistoryRepository historyRepository){
        this.historyRepository = historyRepository;
    }


    public CompanyHistory save(CompanyHistory history){
       return historyRepository.save(history);
    }

    public CompanyHistory convertCompanyToHistory(CompanyEntity companyEntity){
        CompanyHistory companyHistory = new CompanyHistory();
        companyHistory.setCompanyName(companyEntity.getCompanyName());
        companyHistory.setUsername(companyEntity.getUsername());
        companyHistory.setCompanyEntity(companyEntity);

        return companyHistory;
    }



}
