import com.sun.source.tree.Tree;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class TreeNode<T> implements Iterable<T>  {
    private T t;
    private TreeNode<T> left, right;
    private TreeNode<T> parent = null;

    public T getT() {
        return t;
    }


    public TreeNode(T t, TreeNode<T> l, TreeNode<T> r){
        this.t=t;
        this.left=l;
        this.right=r;
        if(l!=null) l.parent=this;
        if(r!=null) r.parent=this;
    }


    public static <T> TreeNode<T> foglia(T r){
        return new TreeNode<>(r,null,null);
    }
    public static <T> @NotNull TreeNode<T> l(T r, TreeNode<T> l){
        return new TreeNode<>(r,l,null);
    }
    public static <T> @NotNull TreeNode<T> r(T t, TreeNode<T> r){
        return new TreeNode<T>(t,null,r);
    }
    public static <T> @NotNull TreeNode<T> both(T t, TreeNode<T> l, TreeNode<T> r){
        return new TreeNode<T>(t,l,r);
    }


    @Override
    public boolean equals(Object obj){
        if(! (obj instanceof TreeNode)){
            return false;
        }

        TreeNode<T> test = (TreeNode<T>) obj;
        Iterator<T> t1=this.iterator();
        Iterator<T> t2=test.iterator();
        while(t1.hasNext()){
            if(!t2.hasNext()){
                return false;
            }
            if(t1.next()!=t2.next()){
                return false;
            }
        }
        return !t2.hasNext();
    }



    public Iterator<T> iterator(){
        return new Iterator<T>() {
            private  TreeNode<T> root=TreeNode.this;
            @Override
            public boolean hasNext() {

                return root!=null;
            }

            @Override
            public T next() {
                T val=root.t;
                if(root.left!=null){
                    root= root.left;
                }else if(root.right!=null){
                    root= root.right;
                }else{
                    while(root.parent!=null){
                        final TreeNode<T> tmp=root;
                        root=root.parent;
                        if(root.right!=null && root.right!=tmp){
                            root=root.right;
                            break;
                        }
                    }
                }
                return val;
            }
        };

    }

    public static void main2(String[] args){
        TreeNode<Integer> t1=new TreeNode<>(14,null,null);
        TreeNode<Integer> t2=new TreeNode<>(14,
                TreeNode.both(12,
                        TreeNode.l(15,null),
                        TreeNode.r(16,
                                TreeNode.l(22,null))),
                TreeNode.r(16,null)
                );
        TreeNode<Integer> t3= new TreeNode<>(14,
                TreeNode.both(12,
                        TreeNode.l(15,null),
                        TreeNode.r(16,
                                TreeNode.l(22,null))),
                TreeNode.r(16,null)
        );

        System.out.println(t3.equals(t2));
    }


        public static void main(String[] args) {
            TreeNode<Integer> t1 =
                    TreeNode.both(1,
                            TreeNode.both(2,
                                    TreeNode.foglia(3),
                                    TreeNode.foglia(4)),
                            TreeNode.r(5,
                                    TreeNode.both(6,
                                            TreeNode.foglia(7),
                                            TreeNode.foglia(8))));

            TreeNode<Integer> t2 =
                    TreeNode.both(1,
                            TreeNode.r(5,
                                    TreeNode.both(6,
                                            TreeNode.foglia(7),
                                            TreeNode.foglia(8))),
                            TreeNode.both(2,
                                    TreeNode.foglia(3),
                                    TreeNode.foglia(4)));




            System.out.print("equality: ");
            System.out.println(t1.equals(t2) + ", " + (t1.left != null ? t1.left.equals(t2.right) : ""));

            // test iterator
            System.out.print("iterator: ");
            for (Integer n : t1) {
                System.out.printf("%d ", n);
            }
            System.out.println();
    }

};


