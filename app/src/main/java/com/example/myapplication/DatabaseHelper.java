package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAppDatabase";
    private static final int DATABASE_VERSION = 21;
    private static final String TABLE_USER = "user";
    private static final String TABLE_RECIPES = "recipes";

    private static final String TABLE_BAKING_RECIPES = "baking_recipes";

    private static final String COLUMN_RATING = "recipe_rating";

    private static final String COLUMN_ID = "id";

    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String COLUMN_RECIPE_ID = "recipe_id";
    private static final String COLUMN_RECIPE_NAME = "recipe_name";
    private static final String COLUMN_RECIPE_ZUBEREITUNG = "recipe_zubereitung";

    private static final String COLUMN_RECIPE_ZUTATEN = "recipe_zutaten";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT" + ")";

    private static final String CREATE_TABLE_RECIPES = "CREATE TABLE " + TABLE_RECIPES + "("
            + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_RECIPE_NAME + " TEXT,"
            + COLUMN_RECIPE_ZUTATEN + " TEXT,"
            + COLUMN_RECIPE_ZUBEREITUNG + " TEXT,"
            + COLUMN_RATING + " REAL" + ")";


    private static final String CREATE_TABLE_BAKING_RECIPES = "CREATE TABLE " + TABLE_BAKING_RECIPES + "("
            + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_RECIPE_NAME + " TEXT,"
            + COLUMN_RECIPE_ZUTATEN + " TEXT,"
            + COLUMN_RECIPE_ZUBEREITUNG + " TEXT,"
            + COLUMN_RATING + " REAL" + ")";


    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_RECIPES);
        db.execSQL(CREATE_TABLE_BAKING_RECIPES);

        // Hier rufen Sie die Methode addExampleRecipes() auf
        addExampleRecipes(db);
        addExampleBakingRecipes(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAKING_RECIPES);
        // create new tables
        onCreate(db);
    }

    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long id = db.insert(TABLE_USER, null, values);
        db.close();
        return id;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }


    public long addRecipe(String recipeName, String zutaten, String zubereitung, String ratings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, recipeName);
        values.put(COLUMN_RECIPE_ZUTATEN, zutaten);
        values.put(COLUMN_RECIPE_ZUBEREITUNG, zubereitung);
        values.put(COLUMN_RATING, ratings);
        long id = db.insert(TABLE_RECIPES, null, values);
        db.close();
        return id;
    }

    public long addBakingRecipe(String recipeName, String zutaten, String zubereitung, String ratings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, recipeName);
        values.put(COLUMN_RECIPE_ZUTATEN, zutaten);
        values.put(COLUMN_RECIPE_ZUBEREITUNG, zubereitung);
        values.put(COLUMN_RATING, ratings);
        long id = db.insert(TABLE_BAKING_RECIPES, null, values);
        db.close();
        return id;
    }

    public void deleteRecipe(String recipeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_RECIPE_NAME + "=?";
        String[] whereArgs = {recipeName};
        db.delete(TABLE_RECIPES, whereClause, whereArgs);
        db.close();
    }

    public void deleteBakingRecipe(String recipeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_RECIPE_NAME + "=?";
        String[] whereArgs = {recipeName};
        db.delete(TABLE_BAKING_RECIPES, whereClause, whereArgs);
        db.close();
    }

    public String[] getAllRecipeNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_RECIPE_NAME};
        Cursor cursor = db.query(TABLE_RECIPES, columns, null, null, null, null, null);
        String[] recipeNames = new String[cursor.getCount()];
        int index = 0;
        while (cursor.moveToNext()) {
            recipeNames[index++] = cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_NAME));
        }
        cursor.close();
        db.close();
        return recipeNames;
    }


    public String[] getAllBakingRecipeNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_RECIPE_NAME};
        Cursor cursor = db.query(TABLE_BAKING_RECIPES, columns, null, null, null, null, null);
        String[] recipeNames = new String[cursor.getCount()];
        int index = 0;
        while (cursor.moveToNext()) {
            recipeNames[index++] = cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_NAME));
        }
        cursor.close();
        db.close();
        return recipeNames;
    }

    public String getIngredientsAndPreparationForRecipe(String recipeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_RECIPE_ZUTATEN, COLUMN_RECIPE_ZUBEREITUNG};
        String selection = COLUMN_RECIPE_NAME + "=?";
        String[] selectionArgs = {recipeName};
        Cursor cursor = db.query(TABLE_RECIPES, columns, selection, selectionArgs, null, null, null);
        String recipe = "";

        if (cursor != null && cursor.moveToFirst()) {
            String zutaten = cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_ZUTATEN));
            String zubereitung = cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_ZUBEREITUNG));
            recipe = "Zutaten für das Rezept:\n" + zutaten + "\n\n" +
                    "Zubereitung:\n" + zubereitung;
            cursor.close();
        }

        db.close();
        return recipe;
    }

    public String getIngredientsAndPreparationForBakingRecipe(String recipeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_RECIPE_ZUTATEN, COLUMN_RECIPE_ZUBEREITUNG};
        String selection = COLUMN_RECIPE_NAME + "=?";
        String[] selectionArgs = {recipeName};
        Cursor cursor = db.query(TABLE_BAKING_RECIPES, columns, selection, selectionArgs, null, null, null);
        String recipe = "";

        if (cursor != null && cursor.moveToFirst()) {
            String zutaten = cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_ZUTATEN));
            String zubereitung = cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_ZUBEREITUNG));
            recipe = "Zutaten für das Rezept:\n" + zutaten + "\n\n" +
                    "Zubereitung:\n" + zubereitung;
            cursor.close();
        }

        db.close();
        return recipe;
    }

    public String getRatingForRecipe(String recipeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_RATING};
        String selection = COLUMN_RECIPE_NAME + "=?";
        String[] selectionArgs = {recipeName};
        Cursor cursor = db.query(TABLE_RECIPES, columns, selection, selectionArgs, null, null, null);
        String rating = "0";

        if (cursor != null && cursor.moveToFirst()) {
            rating = cursor.getString(cursor.getColumnIndex(COLUMN_RATING));
            cursor.close();
        }

        db.close();
        return rating;
    }

    public String getRatingForBakingRecipe(String recipeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_RATING};
        String selection = COLUMN_RECIPE_NAME + "=?";
        String[] selectionArgs = {recipeName};
        Cursor cursor = db.query(TABLE_BAKING_RECIPES, columns, selection, selectionArgs, null, null, null);
        String rating = "0";

        if (cursor != null && cursor.moveToFirst()) {
            rating = cursor.getString(cursor.getColumnIndex(COLUMN_RATING));
            cursor.close();
        }

        db.close();
        return rating;
    }

    public long getRecipeIdByName(String recipeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        long recipeId = -1;
        String[] columns = {COLUMN_RECIPE_ID};
        String selection = COLUMN_RECIPE_NAME + "=?";
        String[] selectionArgs = {recipeName};
        Cursor cursor = db.query(TABLE_RECIPES, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            recipeId = cursor.getLong(cursor.getColumnIndex(COLUMN_RECIPE_ID));
            cursor.close();
        }
        db.close();
        return recipeId;
    }

    public long getBakingRecipeIdByName(String recipeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        long recipeId = -1;
        String[] columns = {COLUMN_RECIPE_ID};
        String selection = COLUMN_RECIPE_NAME + "=?";
        String[] selectionArgs = {recipeName};
        Cursor cursor = db.query(TABLE_BAKING_RECIPES, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            recipeId = cursor.getLong(cursor.getColumnIndex(COLUMN_RECIPE_ID));
            cursor.close();
        }
        db.close();
        return recipeId;
    }

    public void rateRecipe(String recipeName, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RATING, rating);

        Log.d("DatabaseHelper", "recipeName: " + recipeName + ", rating: " + rating);

        String selection = COLUMN_RECIPE_NAME + "=?";
        String[] selectionArgs = {recipeName};
        int rowsAffected = db.update(TABLE_RECIPES, values, selection, selectionArgs);
        db.close();
        if (rowsAffected == 0) {

        }
    }

    public void rateBakingRecipe(String recipeName, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RATING, rating);

        Log.d("DatabaseHelper", "recipeName: " + recipeName + ", rating: " + rating);

        String selection = COLUMN_RECIPE_NAME + "=?";
        String[] selectionArgs = {recipeName};
        int rowsAffected = db.update(TABLE_BAKING_RECIPES, values, selection, selectionArgs);
        db.close();
        if (rowsAffected == 0) {
        }
    }

    private void addExampleBakingRecipes(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, "Erdbeer Tiramisu");
        values.put(COLUMN_RECIPE_ZUTATEN, "250 g Mascarpone\n" +
                "250 g Magerquark\n" +
                "2-3 EL Mandellikör\n" +
                "50-75 g Zucker\n" +
                "ca. 250 g Erdbeeren\n" +
                "ca. 75 g Amarettini\n" +
                "ca. 75 ml Espresso\n" +
                "Kakao zum Bestäuben\n" +
                "rosa Baisertuffs zum Verzieren\n"
                );
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Mascarpone, Quark, Likör und Zucker glatt rühren. Erdbeeren waschen und 2 Stück beiseitelegen. Übrige Erdbeeren putzen und in Scheiben schneiden. Erdbeerscheiben an den Glasrand stellen.\n" +
                "Jeweils ca. 5 Amarettini auf den Glasboden geben und mit 1 Esslöffel Espresso beträufeln. Darauf 2-3 Esslöffel Creme geben und mit 5 Amaretti belegen. Mit je 1 Esslöffel Espresso beträufeln, darauf 2-3 Esslöffel Creme geben und mit Erdbeerscheiben belegen. Übrige Creme auf die Erdbeeren geben und ca. 30 Minuten kalt stellen.\n" +
                "2 Erdbeeren halbieren. Creme vor dem Servieren mit Kakao bestäuben. Mit Erdbeerhälften und zerbröselten Baiser verzieren.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_BAKING_RECIPES, null, values);

            values.put(COLUMN_RECIPE_NAME, "Schneemoussetorte");
            values.put(COLUMN_RECIPE_ZUTATEN, "1 Ei Größe M\n" +
                    "31g weiche Butter + etwas zum einfetten\n" +
                    "81g + 4-5 EL Zucker\n" +
                    "Salz\n" +
                    "3/4 Packung Vanillezucker\n" +
                    "38g Mehl\n" +
                    "1/2 gestr. TL Backpulver\n" +
                    "1 1/4 EL Milch\n" +
                    "1 EL Mandelblättchen\n" +
                    "125g Rhabarber\n" +
                    "1/4 Pck. Vanillepuddingpulver\n" +
                    "100g Schlagsahne\n" +
                    "Puderzucker zum Bestäuben\n"
            );
            values.put(COLUMN_RECIPE_ZUBEREITUNG, "Eine Springform (26 cm Ø) fetten. Eier trennen. Butter, 125 g Zucker, 1 Prise Salz und Vanillezucker mit den Schneebesen des Handrührgeräts cremig rühren. Eigelb einzeln unterrühren. Mehl und Backpulver mischen und im Wechsel mit der Milch kurz unterrühren. Hälfte Teig in die Form füllen und glattstreichen.\n" +
                    "Eiweiß steif schlagen, 200 g Zucker dabei einrieseln lassen. Weiterschlagen, bis sich der Zucker gelöst hat. Hälfte Eischnee auf den Teig in die Form streichen. 2 EL Mandelblättchen darüberstreuen. \n" +
                    "Im vorgeheizten Backofen (E-Herd: 175 °C/Umluft: 150 °C) Ofen 25–30 Minuten backen. Abkühlen lassen, aus der Form lösen, auskühlen lassen. Rest Teig, Eischnee und Mandelblättchen ebenso in die Form füllen und backen. Auskühlen lassen.\n" +
                    "Inzwischen Rhabarber putzen, waschen und in kleine Stücke schneiden. Mit 100 ml Wasser und 4–5 EL Zucker aufkochen und zugedeckt ca. 5 Minuten köcheln.\n" +
                    "Puddingpulver und 6 EL Wasser glattrühren. In den Rhabarber rühren, wieder aufkochen und unter Rühren ca. 1 Minute köcheln. Etwas abkühlen lassen, gleichmäßig auf einem Tortenboden verteilen und kaltstellen.\n" +
                    "Sahne steif schlagen, 2 Päckchen Vanillezucker dabei einrieseln lassen. Auf das ausgekühlte Kompott streichen, zweiten Tortenboden daraufsetzen und mit Puderzucker bestäuben");
            values.put(COLUMN_RATING, 0);
            db.insert(TABLE_BAKING_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Rhabarber-Quarkkuchen");
        values.put(COLUMN_RECIPE_ZUTATEN, "50g Butter\n" +
                        "etwas weiche Butter\n" +
                        "25g Zucker + 25g Zucker + 38g Zucker\n" +
                        "3/4 Salz, 3 Eier + 2 Eigelb\n" +
                        "31g Haferflocken\n" +
                        "20g gemahlene Mandeln\n" +
                        "31g Mehl\n" +
                        "163g Rhababer\n" +
                        "50g Magerquark\n" +
                        "1/4 Pck. Vanillin-Zucker\n" +
                        "Milch\n" +
                        "31g Schlagsahne\n" +
                        "Puderzucker zum Bestäuben\n"
        );
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "200 g Butter, 100 g Zucker und 1 Prise Salz mit den Schneebesen des Handrührgerätes cremig rühren. 1 Ei und 2 Eigelb einzeln darunterrühren. Haferflocken, Mandeln und 125 g Mehl mischen, mit den Knethaken unterkneten\n" +
                "Springform (26 cm Ø) fetten. Teig in die Form geben, mit einem Esslöffel verstreichen und am Rand ca. 2 cm hochdrücken. Boden mit einer Gabel mehrmals einstechen. Im vorgeheizten Backofen (E-Herd: 200 °C/Umluft: 175 °C/Gas: Stufe 3) ca. 15 Minuten vorbacken\n" +
                "Rhabarber putzen, waschen und in ca. 3 cm lange Stücke schneiden. Rhabarber mit 4–5 EL Wasser und 100 g Zucker aufkochen. Dann gut abtropfen, auskühlen lassen\n" +
                "\n" +
                "Rhabarber putzen, waschen und in ca. 3 cm lange Stücke schneiden. Rhabarber mit 4–5 EL Wasser und 100 g Zucker aufkochen. Dann gut abtropfen, auskühlen lassen.\n" +
                "Quark, 150 g Zucker, Vanillin-Zucker, 1 EL Mehl, 2 Eier und Milch mit den Schneebesen des Handrührgerätes glatt verrühren. Sahne steif schlagen und unter die Quarkmasse heben.\n" +
                "Rhabarber auf dem Boden verteilen und mit dem Quarkguss bedecken. Bei gleicher Temperatur 30–40 Minuten zu Ende backen. Rhabarberkuchen in der Form auskühlen lassen. Mit Puderzucker bestäuben. Dazu: Schlagsahne.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_BAKING_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Chocolat malheur mit Birnen");
        values.put(COLUMN_RECIPE_ZUTATEN, "50 g Edelbitterschokolade (mind. 70% Kakao)\n" +
                        "33 g Butter \n" +
                        "2/3 EL Butter \n" +
                        "2/3 Eier + 2 Eigelb (Gr. M)\n" +
                        "20 g Zucker \n" +
                        "2/3 EL Zucker\n" +
                        "10 g (2 leicht gehäufte EL)Mehl\n" +
                        "1 Birne\n" +
                        "33ml Apfelsaft\n"
        );
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Schokolade hacken. Mit 100 g Butter in Stückchen im heißen Wasserbad schmelzen. Etwas abkühlen lassen. Sechs ofenfeste Förmchen (à ca. 150 ml Inhalt) fetten, mit Zucker ausstreuen.\n" +
                        "Ofen vorheizen (E-Herd 175°C/Umluft: 150°C/Gas: Stufe 2). Eier, Eigelb und 60 g Zucker mit den Schneebesen des Handrührgeräts ca. 5 Minuten schaumig schlagen. Mehl und die geschmolzene Schokolade unterrühren.\n" +
                        "Förmchen zu ca. 3/4 mit der Schokomasse füllen. Im heißen Backofen 10–12 Minuten backen.\n" +
                        "Birnen schälen, vierteln, entkernen und in Spalten schneiden. 2 EL Butter in einer Pfanne schmelzen. Birnen darin unter Wenden 3–4 Minuten dünsten. Mit 2 EL Zucker bestreuen und karamellisieren.\n" +
                        "Apfelsaft zugießen und kurz aufkochen. Chocolat malheur aus dem Ofen nehmen, sofort stürzen und mit den Birnen servieren.\n");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_BAKING_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Apfelkuchen");
        values.put(COLUMN_RECIPE_ZUTATEN, "1 Apfel\n" +
                "50 g Butter oder Margarine\n" +
                "50 g Zucker\n" +
                "1/3 Prise Salz " +
                "2/3 Eier (Gr. M)\n" +
                "1/3 Pck. Vanillepuddingpulver (zum Kochen)\n" +
                "83 g Mehl\n" +
                "1 gestr. TL Backpulver\n" +
                "1 2/3 EL Milch\n" +
                "Puderzucker zum Bestäuben\n"
        );
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Äpfel schälen, vierteln, Kerngehäuse herausschneiden und Fruchtfleisch in Würfel schneiden.\n" +
                "Fett, Zucker und Salz mit den Schneebesen des Handrührgerätes weißcremig aufschlagen. Eier nacheinander unterrühren.\n" +
                "Puddingpulver, Mehl und Backpulver mischen. Mehl-Mischung und Milch abwechselnd unter den Teig heben. Äpfel unter den Teig heben. Teig in eine gefettete, mit Mehl ausgestäubte Springform (26 cm Ø) geben und glattstreichen. Im vorgeheizten Backofen (Ober-/Unterhitze: 175 Grad Celsius/ Umluft: 150 Grad Celsius) ca. 45 Minuten backen.\n" +
                "Apfelkuchen aus dem Ofen nehmen, auf einem Kuchengitter auskühlen lassen und aus der Form lösen. Mit Puderzucker bestäuben.\n");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_BAKING_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Schokokuchen");
        values.put(COLUMN_RECIPE_ZUTATEN, "63 g + etwas weiche Butter oder Margarine\n" +
                "63 g Zucker\n" +
                "1/4 Pck. Bourbon-Vanillezucker\n" +
                "1/4 Prise Salz\n" +
                "75 g + etwas Mehl\n" +
                "1 Ei (Gr. M)\n" +
                "16 g Back-Kakao\n" +
                "1/4 Pck. Backpulver\n" +
                "31ml Milch\n" +
                "31ml Wasser\n" +
                "1/4 Pck. ( 125 g)dunkle Kuchenglasur\n"
        );
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "250 g Butter, Zucker, Vanillezucker und Salz mit den Schneebesen des Handmixers cremig schlagen. 300 g Mehl in einer weiteren Schüssel abwiegen. Eier nacheinander, im Wechsel mit jeweils 1 EL des gewogenen Mehls, gut unter die Fett-Zucker-Mischung rühren. Restliches Mehl mit Kakao und Backpulver mischen und zusammen mit der Milch kurz unterrühren. Wasser aufkochen, kurz abkühlen lassen und noch heiß unter den Teig rühren. \n" +
                "Teig in eine gefettete, mit Mehl ausgestäubte Gugelhupf Form (ca. 2 1/2 l Inhalt) geben und glattstreichen. Im vorgeheizten Backofen auf der unteren Schiene (E-Herd: 175 °C/Umluft: 150 °C) ca. 50 Minuten backen. Mit einem Holzspieß in den Kuchen stechen. Bleibt Teig daran kleben, einige Minuten weiterbacken. Fertigen Kuchen in der Form auf einem Kuchengitter ca. 15 Minuten abkühlen lassen. Schokokuchen aus der Form stürzen und auskühlen lassen.\n" +
                "Glasur nach Packungsanweisung schmelzen, gleichmäßig über den Schokokuchen gießen und trocknen lassen.\n");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_BAKING_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Vanillekipferl");
        values.put(COLUMN_RECIPE_ZUTATEN, "1 Vanilleschote\n" +
                "100 g gemahlene Mandeln (ohne Haut)\n" +
                "275 g Mehl + etwas Mehl für die Arbeitsfläche\n" +
                "75 g Zucker\n" +
                "2 Eigelb (Gr. M)\n" +
                "200 g kalte Butter\n" +
                "Salz\n" +
                "50 g Puderzucker\n" +
                "2 Pck. Bourbon-Vanillezucker\n"
        );
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Vanilleschote längs halbieren und das Mark herauskratzen. Mandeln, Mehl, Zucker, Eigelbe, Butter in Stückchen, Vanillemark und 1 Prise Salz zunächst mit den Knethaken des Handmixers, dann mit den Händen, schnell zu einem glatten Teig verkneten. Den Teig zu einer Kugel formen, in Frischhaltefolie wickeln und 1 Stunde kalt stellen.\n" +
                "Teig für die Vanillekipferl auf einer bemehlten Arbeitsfläche zu 3-4 Rollen à 1,5 cm Durchmesser formen. Die Teigrollen jeweils in 4-5 cm lange Stücke schneiden. Teigstücke zu Halbmonden formen. Die Vanillekipferl im vorgeheizten Backofen (E-Herd: 175 °C/Umluft: 160 °C/Gas: Stufe 3) 10-12 Minuten backen, bis die Enden der Kipferl leicht Farbe bekommen.\n" +
                "Puderzucker mit Vanillezucker mischen und sofort über die noch heißen Vanillekipferl sieben, anschließend die Kipferl auf einem Rost oder Kuchengitter vollständig auskühlen lassen.\n");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_BAKING_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Spekulatius");
        values.put(COLUMN_RECIPE_ZUTATEN, "150 g Butter\n" +
                "125 g brauner Zucker\n" +
                "1 Ei (Gr. M)\n" +
                "1 TL gemahlener Zimt \n" +
                "1 Prise gemahlene Gewürznelken \n" +
                "1 Prise gemahlener Kardamom \n" +
                "1 TL abgeriebene Bio-Zitronen-Schale \n" +
                "60 g gemahlene Mandeln \n" +
                "300 g Mehl \n" +
                "1 TL Backpulver\n"
        );
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Butter, Zucker und Ei mit den Schneebesen des Handrührgerätes mindestens 10 Minuten schaumig schlagen. Gewürze, Zitronenschale und gemahlene Mandeln unterrühren. Mehl und Backpulver mischen und unterrühren. \n" +
                "Teig mit den Händen zu einer Kugel formen, in Folie wickeln und ca. 1 Stunde kaltstellen. \n" +
                "Teig auf bemehlter Arbeitsfläche ca. 4 mm dünn ausrollen. Teig mit unterschiedlichen Motiven ausstechen. Reste immer wieder verkneten.\n" +
                "Spekulatius auf mit Backpapier ausgelegte Backbleche geben und ca. 2 Stunden kühl stellen.\n" +
                "Bleche nacheinander im vorgeheizten Backofen (E-Herd: 200 °C/Umluft: 175 °C/Gas: Stufe 3) 10-12 Minuten backen. Herausnehmen und auf einem Kuchengitter auskühlen lassen.\n");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_BAKING_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Spritzgebäck");
        values.put(COLUMN_RECIPE_ZUTATEN, "100 g Puderzucker\n" +
                "200 g weiche Butter\n" +
                "2 EL Milch\n" +
                "1 Ei (Gr. M)\n" +
                "1 Pck. Vanillezucker \n" +
                "1 Prise Salz \n" +
                "250 g Mehl\n"
        );
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Puderzucker und Butter mit den Schneebesen des Handrührgerätes cremig rühren. Nach und nach Milch, Ei, Vanillezucker und Salz hinzugeben und alles zu einer glatten Masse verrühren. Mehl unterheben. Teig in Frischhaltefolie wickeln und mindestens 30 Minuten kühlen. Je kälter der Teig ist, desto besser lässt er sich verarbeiten und desto hübscher gelingen die Plätzchen.\n" +
                "Teig in einen Spritzbeutel mit großer Sterntülle (ø 8 cm) füllen und nach Belieben in Streifen, Kringeln oder Kreisen direkt auf ein mit Backpapier ausgelegtes Blech spritzen. Dabei auf genügend Abstand zwischen den Plätzchen achten, da der Teig leicht zerläuft. Spritzgebäck im vorgeheizten Backofen (E-Herd: 200 °C/Umluft: 175 °C/Gas: Stufe 3) 10-12 Minuten goldgelb backen. Auf einem Kuchengitter auskühlen lassen und nach Belieben dekorieren.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_BAKING_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Spitzbuben");
        values.put(COLUMN_RECIPE_ZUTATEN, "125 g kalte Butter\n" +
                "200 g Mehl (+ etwas für die Arbeitsfläche)\n" +
                "50 g gemahlene Mandeln \n" +
                "Salz \n" +
                "125 g Puderzucker (+ etwas zum Bestäuben)\n" +
                "1 Ei (Größe M)\n" +
                "250 g rotes Johannisbeergelee\n"
        );
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Butter in Stückchen schneiden. Mehl, Mandeln, Salz und Puderzucker in eine Schüssel geben. Butter und Ei zugeben und zuerst mit den Knethaken des Handrührgerätes, dann mit den Händen zügig zu einem glatten Teig verkneten. Teig in Frischhaltefolie wickeln und ca. 30 Minuten kaltstellen.\n" +
                "Johannisbeergelee in einem kleinen Topf erwärmen und abkühlen lassen. Gekühlten Plätzchenteig auf einer bemehlten Arbeitsfläche 2 mm dick ausrollen.\n" +
                "Mithilfe eines runden Plätzchenausstechers kleine Kreise (ca. 6 cm Ø) ausstechen. Von der Hälfte der Kreise in der Mitte einen kleinen Ring (ca. 3 cm) ausstechen. Reste verkneten und ebenso verarbeiten. \n" +
                "Kreise und Ringe auf 2–3 mit Backpapier ausgelegte Backbleche verteilen. Bleche im vorgeheizten Backofen (E-Herd: 175 °C/Umluft: 150 °C) 8–10 Minuten backen. Herausnehmen und abkühlen lassen.\n" +
                "Kreise mit Konfitüre bestreichen, dabei 2–3 mm Rand frei lassen. Ringe mit Puderzucker bestäuben, vorsichtig auf die mit Konfitüre bestrichenen Kreise setzen und leicht andrücken.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_BAKING_RECIPES, null, values);

    }

    private void addExampleRecipes(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_RECIPE_NAME, "Paprika-Hack-Pfanne mit Reis");
        values.put(COLUMN_RECIPE_ZUTATEN, "1 rote Paprikaschote\n" +
                "1 gelbe Paprikaschote\n" +
                "1 grüne Paprikaschote\n" +
                "2 Zwiebeln\n" +
                "1 Knoblauchzehe\n" +
                "2 EL Olivenöl\n" +
                "600 g gemischtes Hackfleisch\n" +
                "Salz\n" +
                "Pfeffer\n" +
                "125 ml Gemüsebrühe\n" +
                "1 Packung (250 g) Express-Reis\n" +
                "1 Glas (400 g) Tomatensoße\n" +
                "Nach Belieben Ingwerpulver\n");
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Paprika putzen, waschen und grob würfeln. Zwiebeln und Knoblauch schälen. Zwiebeln grob und Knoblauch fein würfeln\n" +
                "Öl in einer großen Pfanne erhitzen. Hack darin ca. 6 Minuten unter Wenden braten, dabei mit dem Pfannenwender grob zerkleinern. Mit Salz und Pfeffer würzen. Hack herausnehmen und vorbereitetes Gemüse im Bratfett ca. 5 Minuten braten. Mit Brühe ablöschen\n" +
                "Reis, Hack und Tomatensoße in die Pfanne geben. Aufkochen, in der geschlossenen Pfanne ca. 5 Minuten schmoren. Mit Salz, Pfeffer und Ingwer abschmecken");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Hähnchen Curry");
        values.put(COLUMN_RECIPE_ZUTATEN, "1 Zwiebel\n" +
                "1 Knoblauchzehe\n" +
                "3 Tomaten\n" +
                "500 g Hähnchenfilet\n" +
                "2 EL Öl\n" +
                "1–2 EL Currypulver\n" +
                "1 Dose(n) (425 ml) Kokosmilch\n" +
                "250 g junger Spinat\n" +
                "Salz\n" +
                "Pfeffer\n");
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Zwiebel schälen und fein würfeln. Knoblauch schälen und durch eine Knoblauchpresse drücken. Tomaten waschen, putzen und in Würfel schneiden. Fleisch waschen, trocken tupfen und in Würfel schneiden\n" +
                "Öl in einer Pfanne erhitzen. Fleisch darin unter Wenden 4–5 Minuten braten, dann Zwiebeln und Knoblauch zugeben. Nach ca. 2 Minuten mit Curry bestäuben und kurz andünsten. Tomatenwürfel zugeben und 3–4 Minuten weitergaren. Mit Kokosmilch ablöschen und ca. 10 Minuten köcheln lassen\n" +
                "Spinat waschen, verlesen und trocken schütteln. Spinat zum Hähnchen geben und zusammenfallen lassen. Mit Salz und Pfeffer abschmecken. Dazu schmeckt Naan-Brot");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Ratatouille Pfanne");
        values.put(COLUMN_RECIPE_ZUTATEN, "je 1 Paprikaschoten\n" +
                "2 Zucchini 200g\n" +
                "100g Champignons\n" +
                "2 Knoblauchzehe\n" +
                "2 Stiele Rosmarin\n" +
                "4 Stiele Thymian\n" +
                "100g Tomaten\n" +
                "2 TL Olivenöl\n" +
                "Salz\n" +
                "Pfeffer\n" +
                "6 EL Tomatensaft\n" +
                "2 EL schwarze Oliven mit Stein\n" +
                "4 Scheiben Baguettebrot\n");
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Paprika putzen, waschen und in Stücke schneiden. Zucchini waschen, putzen und in Scheiben schneiden. Champignons säubern, putzen und halbieren. Knoblauch schälen und halbieren. Kräuter waschen, trocken schütteln und kleiner zupfen. Tomate waschen, trocken reiben und klein schneiden.\n" +
                "Öl in einer beschichteten Pfanne erhitzen. Paprika Knoblauch, Zucchinischeiben, Rosmarin und Champignons unter Wende ca. 4 Minuten braten.\n" +
                "Mit Salz und Pfeffer würzen. Mit Tomatensaft ablöschen und kurz einköcheln lassen. Mit Oliven und Baguette servieren.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Ofen Baguettes");
        values.put(COLUMN_RECIPE_ZUTATEN, "80g cremiger Frischkäse\n" +
                "40g Pizzakäse\n" +
                "Pfeffer\n" +
                "2og Salami\n" +
                "10g eingelegte grüne OLiven ohne Stein\n" +
                "0,2 kleine Zwiebeln\n" +
                "20g Ananas in 2-3 Scheiben\n" +
                "20g Schinken\n" +
                "evtl. 0,1 TL Curry\n" +
                "2 Baguette-Brötchen zum Fertigbacken ca. a 75g\n");
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Frischkäse mit dem Schneebesen cremig rühren. Pizzakäse unterrühren und mit Pfeffer würzen. Salami fein würfeln. Oliven hacken. Zwiebel schnälen und fein würfeln.\n" +
                "Salami, Oliven und Zwiebeln mischen und unter die eine Hälfte der Käsecreme rühren. Ananas abtropfen lassen und fein würfeln. Schninken ebenfalls in feine Würfel schneiden. Beides unter die andere Hälfte der Käsecreme rühren. Eventuell mit etwas Churry abschmecken.\n" +
                "Baguette-Brötchen der Länge nach jeweils 3 Mal einscheiden. In die Einschnitte von 5 Baguette-Brötchen Salami-Käsecreme streichen, die übrigen 5 mit Schninken-Ananas-Käsecreme füllen.\n" +
                "Baguette-Brötchen auf ein mit Backpapier ausgelegtes Backblech geben. Im vorgeheizten Backofen Umluft 200 Grad Celcius 15-20 Minuten backen.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Tortellini (mit Käsefüllung)");
        values.put(COLUMN_RECIPE_ZUTATEN, "500g Tortellini (mit Käsefüllung)\n" +
                "1 Bund Rucola\n" +
                "150g Kirschtomaten\n" +
                "100g Mozzarella\n" +
                "4 EL Olivenöl\n" +
                "2 EL Balsamico-Essig\n" +
                "1 TL Senf\n" +
                "Salz\n" +
                "Pfeffer\n");
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Tortellini nach Packungsanleitung kochen, abgießen und abkühlen lassen.\n" +
                "Rucola waschen, trocken schleudern und grob hacken. Kirschtomaten waschen und halbieren. Mozzarella abtropfen lassen und in Würfel schneiden.\n" +
                "Für das Dressing Olivenöl, Balsamico-Essig und Senf verrühren, mit Salz und Pfeffer abschmecken. Tortellini, Rucola, Kirschtomaten und Mozzarella vermengen, mit dem Dressing beträufeln und servieren.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Ofenpfannkuchen");
        values.put(COLUMN_RECIPE_ZUTATEN, "200g Mehl\n" +
                "1 Prise Salz\n" +
                "3 Eier\n" +
                "300ml Milch\n" +
                "1 EL Butter\n");
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Ofen auf 200 Grad Celcius vorheizen.\n" +
                "Mehl und Salz in einer Schüssel vermengen. Eier und Milch hinzufügen und zu einem glatten Teig verrühren.\n" +
                "Eine Auflaufform mit Butter einfetten, Teig hineingeben und im vorgeheizten Ofen ca. 25 Minuten backen, bis der Pfannkuchen goldbraun und aufgegangen ist.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Nudelsalat");
        values.put(COLUMN_RECIPE_ZUTATEN, "500g Nudel\n" +
                "200g Kirschtomaten\n" +
                "1 Gurke\n" +
                "1 Paprikaschote\n" +
                "1 Zwiebel\n" +
                "100g Schinken\n" +
                "100g Käse\n" + 
                "Salz\n" + 
                "Pfeffer\n" +
                "3 EL Olivenöl\n" + 
                "2 EL Balsamicoessig\n");
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Nudeln nach Packungsanleitung kochen, abgießen und abkühlen lassen.\n" +
                "Kirschtomaten halbieren, Gurke und Paprikaschote würfeln, Zwiebeln hacken. Schninken und Käse in Streifen schneiden.\n" +
                "Alle Zutaten in einer großen Schüssel vermengen. Mit Salz, Pfeffer, Olivenöl und Balsamicoessig abschmecken und servieren.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Zwiebel-Sahne Schnitzel");
        values.put(COLUMN_RECIPE_ZUTATEN, "4 Schnitzel\n" +
                        "Salz\n" +
                        "Pfeffer\n" +
                        "2 Zwiebeln\n" +
                        "1 Knoblauchzehe\n" +
                        "1 EL Öl\n" +
                        "200ml Sahne\n" +
                        "200ml Gemüsebrühe\n" +
                        "1 TL Senf\n");
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Schnitzel abwaschen, trocken tupfen und mit Salz und Pfeffer würzen.\n" +
                "Zwiebel in Ringe schneiden, Knoblauch fein hacken. Öl in einer Pfanne erhitzen, Schnitzel darin von beiden Seiten anbraten. Zwiebeln und Knoblauch hinzufügen und glasig dünsten.\n" +
                "Sahne, Gemüsebrühe und Senf hinzufügen, kurz aufkochen lassen und dann ca. 10 Minuten köcheln lassen, bis die Sauce eingedickt ist.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Reispfanne");
        values.put(COLUMN_RECIPE_ZUTATEN, "300g Reis\n" +
                "1 Zwiebel\n" +
                "2 Karotten\n" +
                "1 Paprika\n" +
                "200g Erbsen\n" +
                "4 Eier\n" +
                "6 EL Sojasauce\n" +
                "Salz\n" +
                "Pfeffer\n" +
                "2 EL Öl\n");
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Reis nach Packungsanleitung kochen und abkühlen lassen. Zwiebeln schälen und würfeln, Karotten schälen und in Scheiben schneiden, Paprika entkernen und würfeln.\n" +
                "Öl in einer großen Pfanne erhitzen, Zwiebeln darin glasig anbraten. Karotten und Paprika hinzufügen und ca. 5 Minuten braten.\n" +
                "Erbsen hinzufügen und kurz mitbraten. Reis dazugeben und alles gut vermischen. Eier aufschlagen, über die Reismischung geben und unter Rühren stocken lassen. Mit Sojasauce, Salz und Pfeffer abschmecken und servieren.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_RECIPES, null, values);

        values.put(COLUMN_RECIPE_NAME, "Steakpfanne");
        values.put(COLUMN_RECIPE_ZUTATEN, "500g Rindersteak\n" +
                "2 EL Öl\n" +
                "Salz\n" +
                "Pfeffer\n" +
                "1 Zwiebel\n" +
                "2 Paprikaschoten\n" +
                "200g Champignons\n" +
                "1 Knoblauchzehe\n" +
                "200ml Brühe\n");
        values.put(COLUMN_RECIPE_ZUBEREITUNG, "Rindersteak in Streifen schneiden, mit Salz und Pfeffer würzen. Öl in einer Pfanne erhitzen, Streakstreifen darin scharf anbraten und anschließend herausnehmen.\n" +
                "Zwiebel und Knoblauch fein hacken, Paprikaschoten in Streifen schneiden, Champignons halbieren. Zwiebel und Knoblauch in der Pfanne glasig braten, Paprika und Champignons hinzufügen und ca. 5 Minuten braten.\n" +
                "Mit Brühe ablöschen, Steakstreifen zurück in die Pfanne geben und nochmal kurz erwärmen.");
        values.put(COLUMN_RATING, 0);
        db.insert(TABLE_RECIPES, null, values);
    }

    }
