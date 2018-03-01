package com.unibro.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author NGUYEN DUC THO
 */
public class Global {

    private static final Logger logger = LogManager.getLogger(Global.class.getName());

    public static ClassLoader getCurrentClassLoader(Object defaultObject) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = defaultObject.getClass().getClassLoader();
        }
        return loader;
    }

    public static java.sql.Date getCurrentSqlDatetime() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

    public static java.sql.Date convertDateToSqlDate(java.util.Date today) {
        if (today != null) {
            return new java.sql.Date(today.getTime());
        }
        return null;
    }

    public static java.sql.Timestamp convertDateToSqlTimestamp(java.util.Date today) {
        if (today != null) {
            return new java.sql.Timestamp(today.getTime());
        }
        return null;
    }

    public static java.util.Date convertSqlTimeStampToDate(java.sql.Timestamp today) {
        if (today != null) {
            return new java.util.Date(today.getTime());
        }
        return null;
    }

    public static java.util.Date convertSqlDateToDate(java.sql.Date today) {
        if (today != null) {
            return new java.util.Date(today.getTime());
        }
        return null;
    }

    public static String getRandomString() {
        return MD5(UUID.randomUUID().toString());
    }

    public static int[] excutePrograms(String[] commands) {
        try {
            int[] ret = new int[commands.length];
            for (int i = 0; i < commands.length; i++) {
                logger.info("Excute Command:" + commands[i]);
                Process p = Runtime.getRuntime().exec(commands[i]);
                int exitValue = p.waitFor();
                ret[i] = exitValue;
                logger.info("Exit value = " + exitValue);
            }
            return ret;
        } catch (IOException | InterruptedException ex) {
            logger.error("excutePrograms:" + ex);
            return null;
        }
    }

    public static int excuteProgram1(String[] commands) {
        try {
            Process p = Runtime.getRuntime().exec(commands);
            int exitValue = p.waitFor();
            //ret[i]=exitValue;
            logger.info("Exit value = " + exitValue);
            //}
            return exitValue;
        } catch (IOException | InterruptedException ex) {
            logger.error("excuteProgram1:" + ex);
            return -1;
        }
    }

    public static int excuteProgram(String commands) {
        try {
            logger.info("Excute Command:" + commands);
            Process p = Runtime.getRuntime().exec(commands);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new SyncPipe(p.getInputStream(), System.out).run();
            int exitValue = p.waitFor();
            logger.info("Exit value = " + exitValue);
            return exitValue;
        } catch (IOException | InterruptedException ex) {
            logger.error(ex);
            return -1;
        }
    }

    public static String convertToUnsigned(String Text) {
        String retval = Text;
        String[] replaceString1 = {"ă", "ắ", "ằ", "ẳ", "ẵ", "ặ", "â", "ấ", "ầ", "ả", "ẵ", "ặ", "á", "à", "ẵ", "ả", "ạ"};
        String[] replaceString2 = {"é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", "ề", "ể", "ễ", "ệ"};
        String[] replaceString3 = {"í", "ì", "ỉ", "ĩ", "ị"};
        String[] replaceString4 = {"ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ", "ổ", "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ", "ợ"};
        String[] replaceString5 = {"ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ", "ử", "ữ", "ự"};
        String[] replaceString6 = {"ý", "ỳ", "ỷ", "ỹ", "ỵ"};
        String[] replaceString7 = {"đ"};

        for (String replaceString11 : replaceString1) {
            retval = retval.replaceAll(replaceString11, "a");
        }
        for (String replaceString21 : replaceString2) {
            retval = retval.replaceAll(replaceString21, "e");
        }
        for (String replaceString31 : replaceString3) {
            retval = retval.replaceAll(replaceString31, "i");
        }
        for (String replaceString41 : replaceString4) {
            retval = retval.replaceAll(replaceString41, "o");
        }
        for (String replaceString51 : replaceString5) {
            retval = retval.replaceAll(replaceString51, "u");
        }
        for (String replaceString61 : replaceString6) {
            retval = retval.replaceAll(replaceString61, "y");
        }
        for (String replaceString71 : replaceString7) {
            retval = retval.replaceAll(replaceString71, "d");
        }
        for (String replaceString11 : replaceString1) {
            retval = retval.replaceAll(replaceString11.toUpperCase(), "A");
        }
        for (String replaceString21 : replaceString2) {
            retval = retval.replaceAll(replaceString21.toUpperCase(), "E");
        }
        for (String replaceString31 : replaceString3) {
            retval = retval.replaceAll(replaceString31.toUpperCase(), "I");
        }
        for (String replaceString41 : replaceString4) {
            retval = retval.replaceAll(replaceString41.toUpperCase(), "O");
        }
        for (String replaceString51 : replaceString5) {
            retval = retval.replaceAll(replaceString51.toUpperCase(), "U");
        }
        for (String replaceString61 : replaceString6) {
            retval = retval.replaceAll(replaceString61.toUpperCase(), "Y");
        }
        for (String replaceString71 : replaceString7) {
            retval = retval.replaceAll(replaceString71.toUpperCase(), "D");
        }
        return retval;
    }

    public static String convertStringFilename(String filename) {
        String[] delCharacter = {"\\~", "\\!", "\\@", "\\$", "%20", "\\^", " ", "\\%", "\\&", "\\*", "\\(", "\\)", "\\+", "\\=", "\\{", "\\}", "\\[", "\\]", "\\:", "\\;", "\"", "\\'", "\\<", "\\,", "\\>", "\\.", "\\?", "\\/"};
        String f = filename;
        for (String delCharacter1 : delCharacter) {
            f = f.replaceAll(delCharacter1, "_");
        }
        f = Global.convertToUnsigned(f);
        return f;
    }

    public static String getTailFile(String filename) {
        int i = filename.lastIndexOf(".");
        if (i >= 0) {
            return filename.substring(i + 1);
        }
        return "dat";
    }

    public static String getPrefixFileName(String filename) {
        int i = filename.lastIndexOf(".");
        if (i >= 0) {
            return filename.substring(0, i);
        }
        return filename;
    }

    public static String getNewRandomFileName(String filename) {
        //String prefix=getPrefixFile(filename);
        String tail = getTailFile(filename);
        //prefix=convertStringFilename(prefix);
        //return prefix + "." + tail;
        return Global.getRandomString() + "." + tail;
    }

    public static String getNewStandardFilename(String filename) {
        String prefix = getPrefixFileName(filename);
        String tail = getTailFile(filename);
        prefix = convertStringFilename(prefix);
        return prefix + "." + tail;
    }

    public static File getNewFileName(File f) {
        if (!f.exists()) {
            return f;
        } else {
            String newName = "new" + f.getName();
            return getNewFileName(new File(f.getParent() + "/" + newName));
        }
    }

    public static String getNewFilenameInTime(String filename) {
        String prefix = getPrefixFileName(filename);
        //prefix=convertFilename(prefix);
        String tail = getTailFile(filename);
        Calendar cal = Calendar.getInstance();
        return prefix + cal.getTimeInMillis() + "." + tail;
    }

    public static File convertImageFile(int width, int height, File origin, String convertType) {
        try {
            BufferedImage originalImage = ImageIO.read(origin);
            //int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizeImageJpg = resize(originalImage, width, height);
            String filename = origin.getName();
            String newfilename = filename;
            int i = filename.lastIndexOf(".");
            if (i >= 0) {
                newfilename = filename.substring(0, i) + "." + convertType;
            }
            File retFile;
            if (filename.toLowerCase().equals(newfilename.toLowerCase())) {
                retFile = origin;
            } else {
                retFile = new File(origin.getAbsolutePath() + "/" + newfilename);
            }
            ImageIO.write(resizeImageJpg, convertType, retFile);
            if (retFile.exists() && (!filename.toLowerCase().equals(newfilename.toLowerCase()))) {
                origin.delete();
            }
            return retFile;
        } catch (IOException e) {
            logger.error("Error:" + e);
            return origin;
        }
    }

    public static File convertImageFile(int width, int height, File origin) {
        try {
            BufferedImage originalImage = ImageIO.read(origin);
            //int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizeImageJpg = resize(originalImage, width, height);
            String filename = origin.getName();
            int i = filename.lastIndexOf(".");
            String filetype = filename.substring(i + 1);
            if (filetype.equals("GIF") || filetype.equals("gif")) {
                return origin;
            }
            ImageIO.write(resizeImageJpg, filetype, origin);
            return origin;
        } catch (IOException e) {
            logger.error("Error:" + e);
            return origin;
        }
    }

    public static File convertImageFileWidth(int width, File origin, String convertType) {
        try {
            BufferedImage originalImage = ImageIO.read(origin);
            int w = originalImage.getWidth();
            int h = originalImage.getHeight();
            int height = h * width / w;
            //int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizeImageJpg = resize(originalImage, width, height);
            String filename = origin.getName();
            String newfilename = filename;
            int i = filename.lastIndexOf(".");
            if (i >= 0) {
                newfilename = filename.substring(0, i) + "." + convertType;
            }
            File retFile;
            if (filename.toLowerCase().equals(newfilename.toLowerCase())) {
                retFile = origin;
            } else {
                retFile = new File(origin.getAbsolutePath() + "/" + newfilename);
            }
            ImageIO.write(resizeImageJpg, convertType, retFile);
            if (retFile.exists() && (!filename.toLowerCase().equals(newfilename.toLowerCase()))) {
                origin.delete();
            }
            return retFile;
        } catch (IOException e) {
            logger.error("Error:" + e);
            return origin;
        }
    }

    public static File convertImageFileWidth(int width, File origin) {
        try {
            BufferedImage originalImage = ImageIO.read(origin);
            int w = originalImage.getWidth();
            int h = originalImage.getHeight();
            int height = h * width / w;
            //int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizeImageJpg = resize(originalImage, width, height);
            String filename = origin.getName();
            int i = filename.lastIndexOf(".");
            String filetype = filename.substring(i + 1);
            if (filetype.equals("GIF") || filetype.equals("gif")) {
                return origin;
            }
            ImageIO.write(resizeImageJpg, filetype, origin);
            return origin;
        } catch (IOException e) {
            logger.error("Error:" + e);
            return origin;
        }
    }

    public static void convertImageFileWidth(int width, File origin, File newFile) {
        try {
            BufferedImage originalImage = ImageIO.read(origin);
            int w = originalImage.getWidth();
            int h = originalImage.getHeight();
            int height = h * width / w;
            //int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizeImageJpg = resize(originalImage, width, height);
            String filename = origin.getName();
            int i = filename.lastIndexOf(".");
            String filetype = filename.substring(i + 1);
            ImageIO.write(resizeImageJpg, filetype, newFile);
        } catch (IOException e) {
            logger.error("Error:" + e);
        }
    }

    public static File convertImageFileHeight(int height, File origin, String convertType) {
        try {
            BufferedImage originalImage = ImageIO.read(origin);
            int w = originalImage.getWidth();
            int h = originalImage.getHeight();
            int width = height * w / h;
            //int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizeImageJpg = resize(originalImage, width, height);
            String filename = origin.getName();
            String newfilename = filename;
            int i = filename.lastIndexOf(".");
            if (i >= 0) {
                newfilename = filename.substring(0, i) + "." + convertType;
            }
            File retFile;
            if (filename.toLowerCase().equals(newfilename.toLowerCase())) {
                retFile = origin;
            } else {
                retFile = new File(origin.getAbsolutePath() + "/" + newfilename);
            }
            ImageIO.write(resizeImageJpg, convertType, retFile);
            if (retFile.exists() && (!filename.toLowerCase().equals(newfilename.toLowerCase()))) {
                origin.delete();
            }
            return retFile;
        } catch (IOException e) {
            logger.error("Error:" + e);
            return origin;
        }

    }

    public static File convertImageFileHeight(int height, File origin) {
        try {
            BufferedImage originalImage = ImageIO.read(origin);
            int w = originalImage.getWidth();
            int h = originalImage.getHeight();
            int width = height * w / h;
            //int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizeImageJpg = resize(originalImage, width, height);
            String filename = origin.getName();
            int i = filename.lastIndexOf(".");
            String filetype = filename.substring(i + 1);
            ImageIO.write(resizeImageJpg, filetype, origin);
            return origin;
        } catch (IOException e) {
            logger.error("Error:" + e);
            return origin;
        }

    }

    private static BufferedImage resize(BufferedImage image, int width, int height) {
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static BufferedImage blurImage(BufferedImage image) {
        float ninth = 1.0f / 9.0f;
        float[] blurKernel = {
            ninth, ninth, ninth,
            ninth, ninth, ninth,
            ninth, ninth, ninth
        };

        Map<RenderingHints.Key, Object> map = new HashMap<>();
        map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints hints = new RenderingHints(map);
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
        return op.filter(image, null);
    }

    public static Date getDateFromMiliseconds(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        return cal.getTime();
    }

    public static Date getDateFromMiliseconds(String string) {
        try {
            long longValue = Long.parseLong(string);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(longValue);
            return cal.getTime();
        } catch (NumberFormatException ex) {
            return new Date();
        }
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String getFolderNameByTime() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH);
    }

    public static File createThumbPictureFromVideo(String size, String videoFile, String position, String imageFile) {
        if (Global.excuteProgram1(new String[]{"/usr/local/bin/ffmpeg", "-itsoffset", "-4", "-i", videoFile, "-ss", position, "-vcodec", "mjpeg", "-vframes", "1", "-an", "-f", "rawvideo", "-s", size, "-y", imageFile}) == 0) {
            File f1 = new File(imageFile);
            return f1;
        }
        return null;
    }

    public static String createFolder(String root, String userid) {
        Calendar cal = Calendar.getInstance();
        String extension = cal.get(Calendar.YEAR) + "" + (cal.get(Calendar.MONTH) + 1) + "" + (cal.get(Calendar.DAY_OF_MONTH));
        File f = new File(root + "/" + userid + "/" + extension);
        f.mkdirs();
        Global.excuteProgram("/bin/chmod -R o+rx " + f.getAbsolutePath());
        return userid + "/" + extension;
    }

    public static Date getDateFromString(String date, String format) {
        try {
            SimpleDateFormat simple_format = new SimpleDateFormat(format);
            return simple_format.parse(date);
        } catch (ParseException ex) {
            return new java.util.Date();
        }
    }

    public static Date getRealDateFromString(String date, String format) {
        try {
            SimpleDateFormat simple_format = new SimpleDateFormat(format);
            return simple_format.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String getDateInStringFormat(String format, java.util.Date date) {
        SimpleDateFormat s_format = new SimpleDateFormat(format);
        return s_format.format(date);
    }

    public static String getConfigValue(String key) {
        try {
            Properties configProperties = new Properties();
            InputStream inputStream = Global.class.getClassLoader().getResourceAsStream("config.properties");
            configProperties.load(inputStream);
            return configProperties.getProperty(key);
        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    public static Gson getGsonObject(String dateformat) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(dateformat);
        Gson gson = builder.create();
        return gson;
    }

    public static Gson getGsonObject() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("dd/MM/yyyy'T'HH:mm:ssZ");
        Gson gson = builder.create();
        return gson;
    }
    
    public static boolean checkValidSession(String time) {
        try {
            logger.info(time);
            Long value = Long.parseLong(time);
            Calendar currentTime = Calendar.getInstance();
            Calendar requestTime = Calendar.getInstance();
            requestTime.setTimeInMillis(value);
            Integer delay = Integer.valueOf(Global.getConfigValue("app.delaytime"));
            currentTime.add(Calendar.SECOND, -delay);
            Calendar nextTime = Calendar.getInstance();
            nextTime.add(Calendar.SECOND, delay);
            return requestTime.after(currentTime) & requestTime.before(nextTime);
        } catch (NumberFormatException ex) {
            logger.error(ex);
            return false;
        }
    }

}
