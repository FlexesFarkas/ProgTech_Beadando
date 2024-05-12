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
            return INGREDIENT_SELECTION;
        }
    },
    INGREDIENT_SELECTION {
        @Override
        public OrderState next() {
            return ORDER_PLACED;
        }
    },
    ORDER_PLACED {
        @Override
        public OrderState next() {
            return null;
        }
    };

    public abstract OrderState next();
}