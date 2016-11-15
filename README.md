
# Twitter Stream Analysis using Heron (A Project in UMKC Hackathon - Fall 2016)

###Team Members:

Sai Venkatesh Gatiganti

Karthik Reddy Vundela

Sri Chaitanya Patluri

###Topologies:

####Real Time Trends:

The Entire Twitter Feed is combed in Real time to find the Emerging
Trends using Keywords and Hashtags. These trends often are
responsible for major events and creating buzz around the world.

We used Heron to identify top 10 trends from a stream of continuous
tweets by breaking the tweets in to words and hashtags. Continuously
keeping track of these hashtags gives us the emerging trends as they
are passed through heron.

####Real Time BI:

People around the world use various platforms and devices to access
twitter everyday. Observing the number of users for various platforms
can give advertisers to target the users with user specific ads.

We used Heron to identify various platforms from the tweets by
extracting the metadata from the data which contains details of the
platform being used by the user.

####Heatmap:

People around the world use Twitter everyday. By observing the
number of users geographically would give users what exactly is
happening around their world.

We used Heron to create a dynamic heatmap of twitter users around
the world by extracting latitude and longitude coordinates from
metadata of the tweets.

###[Project Video](https://www.youtube.com/watch?v=Rz3vfu-RC0k)

Language: Java

Visualization: D3.js & Google Maps(Heatmap)
