package pe.com.bank.trading.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import pe.com.bank.trading.document.TradingDocument;
import pe.com.bank.trading.dto.GeneratedTrade;
import pe.com.bank.trading.repository.TradingRepository;
import pe.com.bank.trading.service.TradingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
public class TradingServiceImpl implements TradingService {

    TradingRepository tradingRepository;


    @Bean
    Consumer<GeneratedTrade> bootcoinTrading() {
        return message -> {
            log.info("leonel: " + message);
        };
    }

  /*  @Bean
    public Function<Flux<GeneratedTrade>, Mono<Void>> input() {
        return flux -> flux.flatMap(a -> accountRepository.save(a)).then();
    }*/

    public Mono<TradingDocument> createTrading(TradingDocument tradingDocument){
        return tradingRepository.save(tradingDocument);
    }

    public Mono<TradingDocument> updateTrading(TradingDocument tradingDocument,String tradingId){
        return tradingRepository.findById(tradingId).flatMap(trading -> {
            trading.setAmountChange(tradingDocument.getAmountChange() != 0 ? tradingDocument.getAmountChange():trading.getAmountChange());
            trading.setBootcoindId(tradingDocument.getBootcoindId() !=null ? tradingDocument.getBootcoindId():trading.getBootcoindId());
            trading.setPaymentType(tradingDocument.getPaymentType() !=null ? tradingDocument.getPaymentType():trading.getPaymentType());
            trading.setState(tradingDocument.getState() !=null ? tradingDocument.getState():trading.getState());
            return tradingRepository.save(trading);
        });
    }

    public Flux<TradingDocument> getALlTrading(){
        return tradingRepository.findAll();
    }

    public Mono<TradingDocument> getTradingById(String id){
        return tradingRepository.findById(id);
    }




}
