# Release new artifact:
## 1. release it to staging
```shell
mvn clean deploy -P release
```


## 2. Promote the release
If the repository was successfully closed, now you’re able to promote it to release.

```shell

mvn nexus-staging:release -DstagingRepositoryId=ltdclear-solutions-1002
```

# References:

1. [How to Create a Java Library: From Scratch to Maven Central](https://dzone.com/articles/how-to-create-a-java-library-from-scratch-to-maven )