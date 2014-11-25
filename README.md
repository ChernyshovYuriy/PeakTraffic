PeakTraffic
===========

This is a simple Java application that solves provided problem: https://www.codeeval.com/public_sc/49

In order to solve a problem of finding sub-set inside particular set Bron–Kerbosch algorithm is used.
It described at http://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm.

Thanks to the Ewgenij Proschak and Contributors for the Java version overview:
http://showme.physics.drexel.edu/usefulchem/Software/Drexel/Cheminformatics/Java/st2d/jgrapht-0.7.0/src/org/jgrapht/alg/BronKerboschCliqueFinder.java

This project is written in pure Java and driven by Gradle.

Requirements (I use Linux for the development):
- Git client (apt-get install git)
- Gradle 2.1 (do not use 'apt-get install' as it installs version 1.8).
  - http://www.gradle.org/downloads - download version 2.1 and unzip it
  - add GRADLE_HOME to the PATH (environment variables):
    - open confoguration file: sudo gedit ~/.bashrc
    - at the end of the text add new lines:
      GRADLE_HOME=/home/user/software/gradle-2.1
      PATH="$PATH:$GRADLE_HOME/bin"
    - close editor and update variables: source ~/.bashrc
  - verify that gradle is installed by perform: gradle -v
- Clone project from the repository: git clone https://github.com/ChernyshovYuriy/PeakTraffic.git ~/development/PeakTraffic
- Navigate to the cloned direcotry: cd ~/development/PeakTraffic
- Now it is possible to list all tasks that gradle provides by executing: 'gradle tasks'
- Execute: 'gradle assemble' in order to assemble project
- Execute: 'gradle test --info' in order to run all available tests

How to run:
- Navigate to the '/build/classes/main' (assuming that You are locating at the root of the project)
- Execute: 'java Main ../../../src/main/resources/input.txt' (or any other input data file, there are some in the 'resources' directory)

Project is created with the assumption that input data are in correct format thus many bound conditions check points are missed. The goal of the project - provide efficient algorithm rather than robustness application.
