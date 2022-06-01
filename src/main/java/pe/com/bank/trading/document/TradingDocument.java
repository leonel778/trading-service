package pe.com.bank.trading.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "trading")
public class TradingDocument {

    @Id
    private String tradingId;
    private int amountChange;
    private String paymentType;
    private String bootcoindId;
    private String state;

}
