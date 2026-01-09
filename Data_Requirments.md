Data Requirements for Social media.

Each user must have a unique account that consists of a unique identifier for each user, a username, an email, a password, and when the account was created. The functional requirements would consist of the user to register, log in, and log out, the system must validate email and username uniqueness and the password must be encrypted before storage.

Each user has exactly one profile that includes their unique identifier, a bio description, and a profile picture stored as a URL to the profile image. Each profiles will automatically create when a user registers, users can update their bio and profile picture, and each profile will have an option to be public or privately viewed.

Users can create multiple posts that will include a post unique identifier, user identifier, format, content, and time the post was created. Users can create, view and delete their own posts, display on users profile.

Users can comment on posts and reply to other comments with a comment unique identifier, user id, post id, content of the comment, and time the comment was created. Each comment belongs to one post and one user, but people can make N number of comments.

Users can like posts, but only once per post being identified by the user id, post id, and time the like was created. Each user and post id must be unique and users can unlike a post. The system will also count the total likes and comments on each post

Users can like comments but only one per comment being stored with their user_id, comment_id, and time of comment creation. Users can also comment on another comment. 

Users can follow and unfollow other users as many times as they would like. This is stored in follower_id and followed_id.



