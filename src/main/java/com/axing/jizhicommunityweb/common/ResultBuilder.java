package com.axing.jizhicommunityweb.common;


public class ResultBuilder {

    private final int status;
    private final int code;
    private final String msg;
    private final Object data;
    private final String action;
    private final String operation;


    public ResultBuilder(int status, int code, String msg, Object data, String action, String operation) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.action = action;
        this.operation = operation;
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public String getAction() {
        return action;
    }

    public String getOperation() {
        return operation;
    }

    public static Builder state(ResultType type) {
        return new Builder(type);
    }

    public static Builder state(ResultType type, String msg) {
        return new Builder(type, msg);
    }

    public static class Builder {

        private int status;

        private ResultType type;

        private String msg;

        private Object data;

        private String action;

        private String operation;

        Builder(ResultType type) {
            if (1000 == type.getCode()){
                this.status = 0;
            }else {
                this.status = 1;
            }
            this.type = type;
            this.msg= type.getMsg();
        }

        Builder(ResultType type, String msg) {
            if (1000 == type.getCode()){
                this.status = 0;
            }else {
                this.status = 1;
            }
            this.type = type;
            this.msg = msg;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder action(String action) {
            this.action = action;
            return this;
        }
        public Builder operation(String operation) {
                    this.operation = operation;
                    return this;
                }

        public ResultBuilder build() {
            return new ResultBuilder(this.status, this.type.getCode(), this.msg, this.data, this.action,this.operation);
        }
    }

    @Override
    public String toString() {
        return "ResultBuilder{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", action='" + action + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }
}
