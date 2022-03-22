Feature:  You are working in the backend team that exposes the service:
  https://jsonplaceholder.typicode.com/ which has the following endpoints:
#
#  1. Make posts: https://jsonplaceholder.typicode.com/posts
#  2. Comment on posts: https://jsonplaceholder.typicode.com/comments
#  3. List of users: https://jsonplaceholder.typicode.com/users

#  @Jsonplace
  Scenario Outline: Test that existing comments can be retrieved with a GET request
    Given service is up and running
    When i search for "<id>" of a comment with a GET method
    Then i should get the correct "<id>", "<name>", "<email>" and "<body>" returned with status code of 200
    Examples:
      | id | name                                      | email                  | body                                                                                                                                                                        |
      | 2  | quo vero reiciendis velit similique earum | Jayne_Kuhic@sydney.com | est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et |
      | 5  | vero eaque aliquid doloribus et culpa     | Hayden@althea.biz      | harum non quasi et ratione\ntempore iure ex voluptates in ratione\nharum architecto fugit inventore cupiditate\nvoluptates magni quo et                                     |


  @Jsonplace
  Scenario Outline: Test that new comments can be created with the POST request
    Given service is up and running
    When I create a new comment with the following details "<postId>","<name>", "<email>" and "<body>",
    Then i should get the correct "<postId>","<name>", "<email>" and "<body>" returned and with status code of 201
    Examples:
      | postId | name           | email                      | body                         |
      | 7      | my new comment | lateefAbdulsalam@yahoo.com | I like this new post so much |


  @Json
  Scenario Outline: Test that a new post can be created with the POST request
    Given service is up and running
    When I create a new comment with the following details "<userId>","<title>" and "<body>",
    Then i should get the correct response "<userId>","<title>", "<body>" and "<id>" returned and with status code of 201
    Examples:
      | userId | title               | body                                                                                    | id  |
      | 3      | My Holiday to Paris | I went on holiday to Malaga and i enjoyed it so much with my family. Here are my photos | 101 |

  @Json
  Scenario Outline: Test that an update to a post can be created with the PUT request
    Given service is up and running
    When I create an update to a new post with the following details "<userId>","<title>" and "<body>",
    Then i should get the correct response "<userId>","<title>", "<body>" and "<id>" returned with status code of 200
    Examples:
      | userId | title               | body                                                                                                  | id |
      | 3      | My Holiday to Texas | I went on holiday to Houston Texas and it was a fun galore with my family. Here are the family photos | 3  |

  @JsonP
  Scenario Outline: Test that a new user account can be created with the POST request
    Given service is up and running
    When I create a new user with the following details "<name>","<username>", "<street>", "<suite>", "<phone>" and "<catchPhrase>",
    Then i should get the correct response "<name>","<username>", "<street>", "<suite>", "<phone>" and "<catchPhrase>" returned with status code of 201
    Examples:
      | name           | username | street         | suite     | phone          | catchPhrase                       |
      | Peace Mustapha | Pmustee  | Ozil Extension | Suite 847 | 1-463-123-4447 | Face to face bifurcated interface |