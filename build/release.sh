#!/bin/bash
branch_name=$(git symbolic-ref HEAD 2>/dev/null | sed "s/^[^\/]*\/[^\/]*\///")
if [[ $branch_name == release/* ]]; then
  major_version=$(echo $branch_name | sed "s/release.//")
  minor_version=$(git tag | grep "v"$major_version | wc -l | tr -d '[[:space:]]')
  version=$major_version.$minor_version
  mvn versions:set -DnewVersion=$version 
  mvn clean test
  if [ $? -eq 0 ]; then
    version=v$version
    git checkout -b tag_$version
    git commit -a -m "Updated version numbers for $version"
    git tag $version
    git push origin $version
    mvn deploy -DskipTests=true
    git checkout $branch_name
    git branch -D tag_$version
  else
    echo "Not tagging as tests failed"
  fi
fi
