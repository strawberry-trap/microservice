package message.constants;

/**
 * @author rory.k
 * common messages for rabbitMQ
 * */
public final class CommonMessage {

    /**
     * member rabbitMQ common messages
     * */
    public static class MemberMessage {
        public static final String MEMBER_GET = "member.get";
        public static final String MEMBER_LIST = "member.list";
        public static final String MEMBER_CREATE = "member.create";
        public static final String MEMBER_UPDATE = "member.update";
        public static final String MEMBER_DELETE = "member.delete";
    }

    /**
     * order rabbitMQ common messages
     * */
    public static class OrderMessage {
        public static final String ORDER_GET = "order.get";
        public static final String ORDER_LIST = "order.list";
        public static final String ORDER_CREATE = "order.create";
        public static final String ORDER_UPDATE = "order.update";
        public static final String ORDER_DELETE = "order.delete";
    }
}
