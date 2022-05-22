public class MyExeption1 extends Exception{
    private String key;
    private String value;

    public MyExeption1(String key, String value) {
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
        return "Ключ и значение одинаковы, зависимость ссылается на файл, где прописана," +
                " будет циклическая зависимость.\nИмя файла : " + key + ",зависимость : " + value;
    }
}
