package com.sstechcanada.resumeapp.models;

import android.os.Parcel;


public class School extends ResumeEvent {
    public School() {
    }

    public static final Creator<School> CREATOR = new Creator<School>() {
        @Override
        public School createFromParcel(Parcel in) {
            return new School(new ResumeEvent(in));
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };

    public School(ResumeEvent event) {
        super(event);
    }

    public String getSchoolName() {
        return getTitle();
    }

    public void setSchoolName(String school) {
        setTitle(school);
    }

    public String getLocation() {
        return getDetail();
    }

    public void setLocation(String location) {
        setDetail(location);
    }

    public String getDegree() {
        return getSubtitle();
    }

    public void setDegree(String degree) {
        setSubtitle(degree);
    }
}
