package pe.com.bank.trading.service;

import pe.com.bank.trading.document.TradingDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TradingService {

    Mono<TradingDocument> createTrading(TradingDocument tradingDocument);

    Mono<TradingDocument> updateTrading(TradingDocument tradingDocument, String tradingId);

    Flux<TradingDocument> getALlTrading();

    Mono<TradingDocument> getTradingById(String id);
}
