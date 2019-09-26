package cn.ommiao.base.entity.order;

public interface BaseEnum<E extends Enum> {
    String getTitle();
    String getDescription();
    E getEnum();
    EnumGroup getEnumGroup();
}
