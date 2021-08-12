package io.asma;


class Connection {
    private String pathway = "";

    public boolean isOpen() {
        return pathway.equals("");
    }

    public void occupy(String path) {
        pathway = path;
    }

    public String getPath() {
        return pathway;
    }
}
