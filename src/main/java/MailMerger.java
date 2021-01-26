import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;


final public class MailMerger extends HashMap<String, HashSet<String>> {
    public MailMerger(String input) throws IOException {
        this(new StringReader(input), "->");
    }

    public MailMerger(File inFile) throws IOException {
        this(new FileReader(inFile), "->");
    }

    public MailMerger(Reader in, String delimiter) throws IOException {
        BufferedReader reader = new BufferedReader(in);
        String line;
        HashMap<String, String> mailToUser = new HashMap<>();
        while ((line = reader.readLine()) != null && !line.equals(""))
            parseLine(mailToUser, line.replaceAll("\\s+", ""), delimiter);

        mergeUsers(mailToUser);
    }

    public void print() {
        for (Entry<String, HashSet<String>> entry: entrySet())
            System.out.println(entry.getKey() + " -> " + String.join(", ", entry.getValue()));
    }

    private void parseLine(HashMap<String, String> tmpMailToUser, String line, String delimiter) throws StringIndexOutOfBoundsException {
        int delPos = line.indexOf(delimiter);
        if (delPos == -1)
            throw new StringIndexOutOfBoundsException("delimiter[" + delimiter + "], not found in[" + line + "]");

        String userName = line.substring(0, delPos);
        String[] mails = line.substring(delPos + delimiter.length()).split(",");
        for (String mail: mails) {
            String tmpUser = tmpMailToUser.get(mail);
            if (tmpUser != null) {
                userName = tmpUser;
                break;
            }
        }

        String finalUserName = userName;
        Stream.of(mails).forEach((mail) -> tmpMailToUser.put(mail, finalUserName));
    }

    private void mergeUsers(HashMap<String, String> tmpMailToUser) {
        for (Entry<String, String> entry: tmpMailToUser.entrySet()) {
            HashSet<String> mails = getOrDefault(entry.getValue(), new HashSet<>());
            mails.add(entry.getKey());
            put(entry.getValue(), mails);
        }
    }
}
