
# play1-conf-accessor

PlayFramework1-application.conf accessor class generator with annotation processing.

## How to use

### 1. Download

conf/dependencies.yml

```yaml
require:
    - play

    # play conf accessor: 推移的依存性を解決しない
    - com.github.asufana -> play1-conf-accessor [1.0,):
                transitive: false

repositories:
    - github:
        type:   iBiblio
        root:   https://raw.github.com/asufana/play1-conf-accessor/mvn-repo/
        contains:
            - com.github.asufana -> *
```

### 2. Eclipse Settings

##### Eclipse preferences

In Eclipse preferences dialog, **Team > Git > Projects** and turn off ```Automatically ignore derived resources by adding them to .gitignore```.

![](https://dl.dropboxusercontent.com/u/425790/images/play1-conf-accessor_01.png)

##### Project properties

In your projects properties dialog, go to **Java Compiler > Annotation Processing** and ensure **Enable annotation processing** is checked. Type ```app``` into **Generated source directory**.

![](https://dl.dropboxusercontent.com/u/425790/images/play1-conf-accessor_02.png)

Next, go to **Java Compiler > Annotation Processing > Factory Path**, select **Add JARs**, and select ```play1-conf-accessor-x.x-jar-with-dependencies.jar```.

![](https://dl.dropboxusercontent.com/u/425790/images/play1-conf-accessor_03.png)


### 3. Use generated class

![](https://dl.dropboxusercontent.com/u/425790/images/play1-conf-accessor_04.png)


