package pe.com.bank.trading.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import pe.com.bank.trading.document.TradingDocument;
import pe.com.bank.trading.dto.Bootcoin;
import pe.com.bank.trading.dto.GeneratedTrade;
import pe.com.bank.trading.dto.WalletOperationDTO;
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

    @Autowired
    StreamBridge streamBridge;

  /*  @Bean
    Consumer<GeneratedTrade> bootcoinTrading() {
        return message -> {
            log.info("leonel: " + message);
        };
    }*/

    @Bean
    public Function<Flux<GeneratedTrade>, Mono<Void>> bootcoinTrading() {
        return flux -> flux.flatMap(this::generateTradeBoot).then();
    }

    public Mono<GeneratedTrade> generateTradeBoot(GeneratedTrade a) {
        return tradingRepository.findById(a.getTradingId()).flatMap(ab -> {
            sendUpdateBootcoin(new Bootcoin(a.getBootcoinId(), ab.getAmountChange()));
            sendUpdateBootcoin(new Bootcoin(ab.getBootcoindId(), ab.getAmountChange()));
            ab.setState("complete");
            return updateTrading(ab, a.getTradingId()).flatMap(ag->{
                WalletOperationDTO wall = new WalletOperationDTO();
                wall.setAmount(ab.getAmountChange());
                wall.setDestinationPhoneNumber(a.getPhoneNumber());
                wall.setSourcePhoneNumber(ab.getPhoneNumber());
                sendPaymentBootCoin(wall);
                return null;
            });
        });
    }


    public void sendUpdateBootcoin(Bootcoin bootcoin) {
        streamBridge.send("trading-updateBootcoin-out-0", bootcoin);
    }

    public void sendPaymentBootCoin(WalletOperationDTO walletOperationDTO) {
        streamBridge.send("iixp9xko-payWallet", walletOperationDTO);
    }


    public Mono<TradingDocument> createTrading(TradingDocument tradingDocument) {
        return tradingRepository.save(tradingDocument);
    }

    public Mono<TradingDocument> updateTrading(TradingDocument tradingDocument, String tradingId) {
        return tradingRepository.findById(tradingId).flatMap(trading -> {
            trading.setAmountChange(tradingDocument.getAmountChange() != 0 ? tradingDocument.getAmountChange() : trading.getAmountChange());
            trading.setBootcoindId(tradingDocument.getBootcoindId() != null ? tradingDocument.getBootcoindId() : trading.getBootcoindId());
            trading.setPaymentType(tradingDocument.getPaymentType() != null ? tradingDocument.getPaymentType() : trading.getPaymentType());
            trading.setState(tradingDocument.getState() != null ? tradingDocument.getState() : trading.getState());
            return tradingRepository.save(trading);
        });
    }

    public Flux<TradingDocument> getALlTrading() {
        return tradingRepository.findAll();
    }

    public Mono<TradingDocument> getTradingById(String id) {
        return tradingRepository.findById(id);
    }


}
