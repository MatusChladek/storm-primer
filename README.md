# Github commit count dashboard

This repository contains implementation in Storm,... for Github commit count dashboard.

Main components:
1. A component that reads from the live feed of commits and produces a single commit message
2. A component that accepts single commit message, extracts developer's email
3. A component that accepts developer's email and updated in-memory map where the key is the email and the value is number of commits for that particular email

> TODO: Recommender System project including how to handle latency and tune Storm topology.  

> Note: This repository is inspired by Storm Applied book from Manning.

