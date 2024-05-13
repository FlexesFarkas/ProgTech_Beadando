package org.kiosk.order;

public enum OrderState {
    START {
        @Override
        public OrderState next() {
            return FOOD_SELECTION;
        }
    },
    FOOD_SELECTION {
        @Override
        public OrderState next() {
            return CART;
        }
    },
    INGREDIENT_SELECTION {
        @Override
        public OrderState next() {
            return CART;
        }
    },
    CART {
        @Override
        public OrderState next() {
            return PAYMENT;
        }
    },
    PAYMENT {
        @Override
        public OrderState next() {
            return PAYMENT_DONE;
        }
    },
    PAYMENT_DONE {
        @Override
        public OrderState next() {
            return null;
        }
    };

    public abstract OrderState next();
}
