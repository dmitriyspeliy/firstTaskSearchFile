public class MyExeption2 extends Exception{
    private String key;
    private String value;

    public MyExeption2(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Файлы " + key + " ссылаются друг на друга " + value;
    }
}
