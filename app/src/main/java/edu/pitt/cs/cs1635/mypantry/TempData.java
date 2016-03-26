package edu.pitt.cs.cs1635.mypantry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Admin on 3/25/16.
 */
public class TempData implements Parcelable{
    String name;
    ArrayList<String> ingredients;
    String directions;

    public TempData(){
        name = "temp name";
        ingredients = new ArrayList<String>();
        directions = "temp directions";
    }

    public TempData(String n, ArrayList<String> ing, String d){
        name = n;
        ingredients = ing;
        directions = d;
    }

    protected TempData(Parcel in) {
        name = in.readString();
        ingredients = in.createStringArrayList();
        directions = in.readString();
    }

    public static final Creator<TempData> CREATOR = new Creator<TempData>() {
        @Override
        public TempData createFromParcel(Parcel in) {
            return new TempData(in);
        }

        @Override
        public TempData[] newArray(int size) {
            return new TempData[size];
        }
    };




    public String getName(){
        return name;
    }

    public String getDirections(){
        return directions;
    }

    public ArrayList<String> getIngredients(){
        return ingredients;
    }

    public String toString(){
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(directions);
        dest.writeList(ingredients);
    }
}
