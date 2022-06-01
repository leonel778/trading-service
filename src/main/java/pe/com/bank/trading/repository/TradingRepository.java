package pe.com.bank.trading.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.com.bank.trading.document.TradingDocument;

@Repository
public interface TradingRepository extends ReactiveMongoRepository<TradingDocument,String> {

}
