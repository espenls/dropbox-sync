package no.segelvik.photo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;



public class DropboxSync {

    // Get your ACCESS_TOKEN from the Dropbox developers website.
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String SYNC_FOLDER = "sync";

    public static void main(String[] args) throws IOException, DbxException {
        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
                Locale.getDefault().toString());

        DbxClient client = new DbxClient(config, ACCESS_TOKEN);
        System.out.println("Linked account: " + client.getAccountInfo().displayName);
        syncDropbox(client);
    }

    private static void syncDropbox(DbxClient client) throws DbxException, IOException {
        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        System.out.println("Files in the root path:");

        createSyncFolder();

        List<String> filesOnDropbox = new ArrayList<String>();
        for (DbxEntry child : listing.children) {
            System.out.println("	" + child.name + ": " + child.toString());

            File f = new File("sync/" +child.asFile().name);
            if(!f.exists()) {
                client.getFile(child.asFile().path, child.asFile().rev, new FileOutputStream(SYNC_FOLDER + "/" +child.name));
            }
            filesOnDropbox.add(SYNC_FOLDER + "/" + child.name);
        }

        deleteFilesFromDisk(getStoredFilesOnDisk(), filesOnDropbox);
    }

    private static File[] getStoredFilesOnDisk(){
        File syncFolder = new File(SYNC_FOLDER);
        return syncFolder.listFiles();
    }

    public static void deleteFilesFromDisk(File[] filesOnDisk, List<String> filesOnDropBox){

        for(File file: filesOnDisk){
            if(!filesOnDropBox.contains(SYNC_FOLDER + "/" +file.getName())){
                System.out.println("Deleting file: " + file.getName());
                file.delete();
            }
        }
    }


    private static void createSyncFolder() {
        File syncFolder = new File(SYNC_FOLDER);
        if(!syncFolder.exists()) {
            syncFolder.mkdir();
        }
    }
}
