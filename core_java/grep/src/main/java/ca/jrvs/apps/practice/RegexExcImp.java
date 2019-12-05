package ca.jrvs.apps.practice;

public class RegexExcImp implements RegexExc{
    /**
     * returns true if the given filename extension is jpg or jpeg (case insensitive)
     * @param filename
     * @return
     */
    @Override
    public boolean matchJpeg(String filename) {
        return filename.matches(".+\\.(jpg|jpeg)");
    }

    /**
     * returns true if the given ip is valid
     * to simplify the problem, IP address range is from 0.0.0.0 to 999.999.999.999
     * @param ip
     * @return
     */
    @Override
    public boolean matchIp(String ip) {
        return ip.matches("(\\d{1,3}\\.){3}\\d{1,3}");
    }

    /**
     * returns true if line is empty (e.g. empty, white space, tabs, etc..)
     * @param line
     * @return
     */
    @Override
    public boolean isEmptyLine(String line) {
        return line.matches("^\\s*$");
    }
}
