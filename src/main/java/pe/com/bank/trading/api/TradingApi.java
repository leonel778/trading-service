package pe.com.bank.trading.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.com.bank.trading.document.TradingDocument;
import pe.com.bank.trading.service.TradingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class TradingApi {


    TradingService tradingService;

    @GetMapping("/getAllTrading")
    public Flux<TradingDocument> getAllTrading(){
        return tradingService.getALlTrading();
    }

    @GetMapping("/getTradingById/{tradingId}")
    public Mono<TradingDocument> getWalletById(@PathVariable String tradingId){
        return tradingService.getTradingById(tradingId);
    }

    @PostMapping("/createTrading")
    public Mono<TradingDocument> saveWallet(@RequestBody TradingDocument tradingDocument){
        return tradingService.createTrading(tradingDocument);
    }

    @PutMapping("/updateWalletById/{tradingId}")
    public Mono<TradingDocument> updateWalletById(@RequestBody TradingDocument tradingDocument,@PathVariable String tradingId){
        return tradingService.updateTrading(tradingDocument, tradingId);
    }




}
