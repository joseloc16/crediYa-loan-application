package co.com.bancolombia.model.loanapplication;

import java.math.BigDecimal;

public record LoanApplication(
    String id,
    String email,
    BigDecimal amount,
    Integer termMonths,
    String loanTypeId,
    String statusId
) {
    public static class Builder {
        private String id;
        private String email;
        private BigDecimal amount;
        private Integer termMonths;
        private String loanTypeId;
        private String statusId;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder termMonths(Integer termMonths) {
            this.termMonths = termMonths;
            return this;
        }

        public Builder loanTypeId(String loanTypeId) {
            this.loanTypeId = loanTypeId;
            return this;
        }

        public Builder statusId(String statusId) {
            this.statusId = statusId;
            return this;
        }

        public LoanApplication build() {
            return new LoanApplication(id, email, amount, termMonths, loanTypeId, statusId);
        }
    }
}