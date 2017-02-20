import { PostId } from './post-id';
import { Location } from './location';


export class Post {
    id: PostId;
    content: String;
    currentTemperature: Number;
    superiorPost: Post;
    responses: Post[];
    location: Location;
    responseAmount: Number;
    reply: Boolean = false;
    replyContent: String;

}