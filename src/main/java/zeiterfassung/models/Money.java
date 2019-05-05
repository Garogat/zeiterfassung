package zeiterfassung.models;

import java.util.Currency;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public class Money {

    private static final Currency EURO = Currency.getInstance("EURO");
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private final BigDecimal amount;
    private final Currency currency;

    public static Money euros(BigDecimal amount) {
        return new Money(amount, EURO);
    }

    Money(String amount) {
        this(new BigDecimal(amount), EURO, DEFAULT_ROUNDING);
    }

    Money(BigDecimal amount) {
        this(amount, EURO, DEFAULT_ROUNDING);
    }

    Money(BigDecimal amount, Currency currency) {
        this(amount, currency, DEFAULT_ROUNDING);
    }

    Money(BigDecimal amount, Currency currency, RoundingMode rounding) {
        this.currency = currency;
        this.amount = amount.setScale(currency.getDefaultFractionDigits(), rounding);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return getCurrency().getSymbol() + " " + getAmount();
    }

}
