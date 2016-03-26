# MyPantry

## Building the Model Files

`greenDao` sets up a separate Java executable that generates the models to be used by the application.  The generated files are checked in version control, so you shouldn't have to run the generators to run the application.  However, if you make changes to the generator file, you'll have to run it again; the Gradle task can be found under `MyPantry > :mypantrygreedaogenerator > Tasks > application > run`.  The output should again be checked into Version Control.

