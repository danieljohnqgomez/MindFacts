package com.danielgomez.mindfacts.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.danielgomez.mindfacts.MindFacts;
import com.danielgomez.mindfacts.R;
import com.danielgomez.mindfacts.adapters.FeedAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ListView listView;
    FeedAdapter adapter;
    SwipeRefreshLayout swipeLayout;
    GetFactFeed feedTask;
    String factProviders[] = {"UberFacts", "WhatTheFFacts", "@Fact", "neverknownfacts", "UnusualFactPage", "TheUnusualFact", "GoogleFacts", "Factsionary"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        Paging paging = new Paging(1, 5);
        feedTask = new GetFactFeed(paging, factProviders);
        feedTask.execute();


        return rootView;
    }

    private class GetFactFeed extends AsyncTask<Void, Void, List<Status>> {

        Paging paging;
        String[] factProviders;
        Twitter unauthenticatedTwitter;

        public GetFactFeed(Paging paging, String[] factProviders) {
            this.paging = paging;
            this.factProviders = factProviders;
            unauthenticatedTwitter = MindFacts.getTwitterFactory().getInstance();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MindFacts.log("Starting");
        }

        @Override
        protected List<twitter4j.Status> doInBackground(Void... voids) {
            List<twitter4j.Status> statuses = new ArrayList<twitter4j.Status>();
            for (String factProvider : factProviders) {
                try {
                    statuses.addAll(unauthenticatedTwitter.getUserTimeline(factProvider, paging));
                } catch (TwitterException e) {
                    e.printStackTrace();
                    continue;
                } catch (Exception e) {
                    MindFacts.logError("Exception occured " + e.getMessage(), e);
                    return null;
                }
            }
            return statuses;
        }

        @Override
        protected void onPostExecute(List<twitter4j.Status> statuses) {
            if (statuses != null) {
                super.onPostExecute(statuses);
                Collections.sort(statuses, Collections.reverseOrder(new Comparator<twitter4j.Status>() {

                    @Override
                    public int compare(twitter4j.Status status, twitter4j.Status status2) {
                        return status.getCreatedAt().compareTo(status2.getCreatedAt());
                    }
                }));
                listView.setAdapter(new FeedAdapter(getActivity(), statuses));
                for (twitter4j.Status status : statuses) {
                    MindFacts.log(status.getCreatedAt() + "" + status.getUser().getName() + status.getUser().getBiggerProfileImageURL());
                }
                swipeLayout.setRefreshing(false);
            }
        }

    }

    @Override
    public void onRefresh() {
        if (!swipeLayout.isRefreshing()){
            feedTask.execute();
        }
    }
}
