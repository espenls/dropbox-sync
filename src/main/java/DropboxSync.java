import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;


/**
 * Created by espsegel on 21.11.15.
 */
public class DropboxSync {

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String APP_KEY = "APP_KEY";
    public static final String APP_SECRET = "APP_SECRET";

    public static void main(String[] args) throws IOException, DbxException {
        // Get your app key and secret from the Dropbox developers website.


        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
                Locale.getDefault().toString());

        DbxClient client = new DbxClient(config, ACCESS_TOKEN);

        System.out.println("Linked account: " + client.getAccountInfo().displayName);


        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) {
            System.out.println("	" + child.name + ": " + child.toString());

            client.getFile(child.asFile().path, child.asFile().rev, new FileOutputStream(child.name));
        }
    }
}
